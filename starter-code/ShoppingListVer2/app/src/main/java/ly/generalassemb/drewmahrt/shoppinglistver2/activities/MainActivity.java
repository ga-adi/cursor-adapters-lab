package ly.generalassemb.drewmahrt.shoppinglistver2.activities;

import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.R;
import ly.generalassemb.drewmahrt.shoppinglistver2.db.DBAssetHelper;
import ly.generalassemb.drewmahrt.shoppinglistver2.db.GroceryCursorAdapter;
import ly.generalassemb.drewmahrt.shoppinglistver2.db.GrocerySQLHelper;
import ly.generalassemb.drewmahrt.shoppinglistver2.models.GroceryItem;

public class MainActivity extends AppCompatActivity {

  private static String TAG = MainActivity.class.getCanonicalName();

  private ListView mListView;
  private GrocerySQLHelper mHelper;
  private GroceryCursorAdapter mAdapter;
  private Cursor mCursor;
  private Button mAddButton; // TODO: make fabulous
  private CoordinatorLayout mLayout;

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
        FragmentManager fragmentManager = getFragmentManager();
        CreateDialogFragment fragment = CreateDialogFragment.newInstance();
        fragment.show(fragmentManager, "create_item_dialog");
      }
    });

    mListView = (ListView)findViewById(R.id.shopping_list_view);
    mListView.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view,
                                         int position, long id) {
            final TextView textView = (TextView)findViewById(R.id.item_id_txt);
            final String itemId = textView.getText().toString();

            final GroceryItem cacheItem = mHelper.createFromId(itemId);
            mHelper.deleteById(itemId);
            mAdapter.swapCursor(mHelper.getGroceries());
            Snackbar.make(mLayout, "Item was deleted",
                          Snackbar.LENGTH_INDEFINITE)
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
    String itemName = bundle.getString(GrocerySQLHelper.COL_ITEM_NAME,
                                       GroceryItem.Builder.NAME);
    String description = bundle.getString(GrocerySQLHelper.COL_DESCRIPTION,
                                          GroceryItem.Builder.DESCRIPTION);
    double price =
        bundle.getDouble(GrocerySQLHelper.COL_PRICE, GroceryItem.Builder.PRICE);
    String itemTypeName = bundle.getString(
        GrocerySQLHelper.COL_TYPE, GroceryItem.Builder.ITEM_TYPE.name());
    final GroceryItem item =
        new GroceryItem.Builder()
            .name(itemName)
            .description(description)
            .price(price)
            .itemType(GroceryItem.ItemType.valueOf(itemTypeName))
            .build();

    mHelper = GrocerySQLHelper.getInstance(MainActivity.this);
    mHelper.create(item);
    mAdapter.swapCursor(mHelper.getGroceries());
  }
}
