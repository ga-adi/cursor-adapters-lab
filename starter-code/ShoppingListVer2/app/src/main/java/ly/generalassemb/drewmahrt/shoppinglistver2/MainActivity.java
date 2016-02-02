package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {

    Cursor mCursor;
    CursorAdapter mCursorAdapter;
    ListView mListView;
    TextView mItemTextView;
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        ShoppingListSQLiteOpenHelper helper = ShoppingListSQLiteOpenHelper.getInstance(MainActivity.this);
        mCursor = helper.getShoppingList();

        mCursorAdapter = new CursorAdapter(MainActivity.this, mCursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                mItemTextView = (TextView)view.findViewById(R.id.item_text_view);
                String itemName = cursor.getString(cursor.getColumnIndex(ShoppingListSQLiteOpenHelper.COL_ITEM_NAME));
                mPriceTextView = (TextView)view.findViewById(R.id.price_text_view);
                String itemPrice = cursor.getString(cursor.getColumnIndex(ShoppingListSQLiteOpenHelper.COL_PRICE));

                mItemTextView.setText(itemName);
                mPriceTextView.setText("$" + itemPrice);

            }
        };

//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                // Define 'where' part of query.
//                String selection = COL_ID + " LIKE ?";
//                // Specify arguments in placeholder order.
//                String[] selectionArgs = { String.valueOf(COL_ID) };
//                // Issue SQL statement.
//                db.delete(SHOPPING_LIST_TABLE_NAME, selection, selectionArgs);
//
//                return false;
//            }
//        });

        mListView = (ListView) findViewById(R.id.shopping_list_view);
        mListView.setAdapter(mCursorAdapter);

    }
}
