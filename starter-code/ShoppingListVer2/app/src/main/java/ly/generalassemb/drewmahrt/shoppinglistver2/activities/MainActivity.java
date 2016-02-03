package ly.generalassemb.drewmahrt.shoppinglistver2.activities;

import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.R;
import ly.generalassemb.drewmahrt.shoppinglistver2.adapters.GroceryCursorAdapter;
import ly.generalassemb.drewmahrt.shoppinglistver2.db.DBAssetHelper;
import ly.generalassemb.drewmahrt.shoppinglistver2.db.GrocerySQLHelper;
import ly.generalassemb.drewmahrt.shoppinglistver2.fragments.GroceryDialogFragment;
import ly.generalassemb.drewmahrt.shoppinglistver2.models.GroceryItem;

public class MainActivity extends AppCompatActivity {

  private static String TAG = MainActivity.class.getCanonicalName();

  private ListView mListView;
  private GrocerySQLHelper mHelper;
  private GroceryCursorAdapter mAdapter;
  private Cursor mCursor;
  private Button mAddButton; // TODO: make fabulous
  private CoordinatorLayout mLayout;
  private FragmentManager mManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Ignore the two lines below, they are for setup
    DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
    dbSetup.getReadableDatabase();

    mHelper = GrocerySQLHelper.getInstance(MainActivity.this);
    mCursor = mHelper.getGroceries();
    mAdapter = new GroceryCursorAdapter(MainActivity.this, mCursor);

    mLayout = (CoordinatorLayout)findViewById(R.id.snack_bar);

    mAddButton = (Button)findViewById(R.id.add_btn);
    mAddButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        mManager = getFragmentManager();
        GroceryDialogFragment fragment = GroceryDialogFragment.newInstance();
        fragment.show(mManager, GroceryDialogFragment.TAG);
      }
    });

    mListView = (ListView)findViewById(R.id.shopping_list_view);
    mListView.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view,
                                         int position, long id) {
            MainActivity.this.showPopUp(view);

            return true;
          }
        });
    mListView.setAdapter(mAdapter);
  }

  @Override
  protected void onResume() {
    super.onResume();

    Bundle bundle = getIntent().getExtras();
    if (bundle == null) {
      return;
    }

    mHelper = GrocerySQLHelper.getInstance(MainActivity.this);

    if (bundle.getSerializable(GrocerySQLHelper.TABLE_NAME) != null) {
      final GroceryItem item =
          (GroceryItem)bundle.getSerializable(GrocerySQLHelper.TABLE_NAME);
      mHelper.create(item);
    } else if (bundle.getSerializable(GroceryDialogFragment.UPDATE) != null) {
      final GroceryItem.Updater updater =
          (GroceryItem.Updater)bundle.getSerializable(
              GroceryDialogFragment.UPDATE);
      mHelper.updateItem(updater);
    }
    mAdapter.swapCursor(mHelper.getGroceries());
  }

  public void showPopUp(View view) {
    PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
    popupMenu.setOnMenuItemClickListener(
        new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            final TextView textView = (TextView)findViewById(R.id.item_id_txt);
            final String itemId = textView.getText().toString();
            switch (item.getItemId()) {
            case R.id.popup_delete:
              onDeleteMenuItemPressed(itemId);
              break;
            case R.id.popup_update:
              onUpdateMenuItemPressed(itemId);
              break;
            default:
              break;
            }
            return false;
          }
        });

    MenuInflater inflater = popupMenu.getMenuInflater();
    inflater.inflate(R.menu.popup, popupMenu.getMenu());
    popupMenu.show();
  }

  private void onDeleteMenuItemPressed(@NonNull String itemId) {

    final GroceryItem cacheItem = mHelper.createFromId(itemId);
    mHelper.deleteById(itemId);
    mAdapter.swapCursor(mHelper.getGroceries());
    Snackbar.make(mLayout, "Item was deleted", Snackbar.LENGTH_INDEFINITE)
        .setAction("UNDO",
                   new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                       mHelper.create(cacheItem);
                       mAdapter.swapCursor(mHelper.getGroceries());
                       Snackbar.make(mLayout, "Item was restored",
                                     Snackbar.LENGTH_SHORT)
                           .show();
                     }
                   })
        .show();
  }

  private void onUpdateMenuItemPressed(@NonNull String itemID) {
    GroceryItem.Updater updater =
        new GroceryItem.Updater(Integer.parseInt(itemID));
    GroceryItem item = mHelper.createFromId(itemID);

    Bundle bundle = new Bundle();
    bundle.putSerializable(GroceryDialogFragment.UPDATE, updater);
    bundle.putSerializable(GroceryDialogFragment.CREATE, item);
    mManager = getFragmentManager();
    GroceryDialogFragment fragment = GroceryDialogFragment.newInstance();
    fragment.setArguments(bundle);
    fragment.show(mManager, GroceryDialogFragment.TAG);
  }
}
