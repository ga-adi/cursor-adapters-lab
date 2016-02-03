package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    mAddButton = (Button) findViewById(R.id.add_btn);
    mAddButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        final GroceryItem item = new GroceryItem.Builder().build();
        mHelper.create(item);
        mAdapter.swapCursor(mHelper.getGroceries());

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

            Toast.makeText(MainActivity.this, "_id is " + itemId,
                           Toast.LENGTH_SHORT)
                .show();
            mHelper.deleteById(itemId);
            mAdapter.swapCursor(mHelper.getGroceries());
            return true;
          }
        });
    mListView.setAdapter(mAdapter);
  }

}
