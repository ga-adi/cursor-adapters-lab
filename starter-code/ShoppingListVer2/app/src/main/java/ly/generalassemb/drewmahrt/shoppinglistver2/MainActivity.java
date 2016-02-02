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
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        final GrocerySQLiteOpenHelper helper = new GrocerySQLiteOpenHelper(MainActivity.this);

        final Cursor cursor = helper.getGroceryList();

        final CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(MainActivity.this).inflate(R.layout.grocery_list_layout,parent,false);
            }
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView typeTextView = (TextView)view.findViewById(R.id.item_type);
                TextView nameTextView = (TextView)view.findViewById(R.id.item_name);
                TextView descTextView = (TextView)view.findViewById(R.id.item_desc);
                TextView priceTextView = (TextView)view.findViewById(R.id.item_price);

                typeTextView.setText(cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_TYPE)));
                nameTextView.setText(cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_ITEM_NAME)));
                descTextView.setText(cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_DESCRIPTION)));
                priceTextView.setText(cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_PRICE)));

            }
        };

        ListView groceryListView = (ListView) findViewById(R.id.shopping_list_view);
        groceryListView.setAdapter(cursorAdapter);

        groceryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                helper.removeItem(id);
                Cursor c = helper.getGroceryList();
                cursorAdapter.swapCursor(c);
                return true;
            }
        });

    }
}
