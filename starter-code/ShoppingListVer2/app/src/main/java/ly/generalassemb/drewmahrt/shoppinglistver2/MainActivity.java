package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        SQLiteOpenHelper helper = (SQLiteOpenHelper) SQLiteOpenHelper.getInstance(MainActivity.this);
        Cursor cursor = helper.getShoppingList();

        String[] columnNames = new String[]{SQLiteOpenHelper.COL_ITEM_NAME, SQLiteOpenHelper.COL_ITEM_DESCRIPTION, SQLiteOpenHelper.COL_ITEM_PRICE, SQLiteOpenHelper.COL_ITEM_TYPE};
        int[] layoutIds = new int[] {R.id.name_text_view,R.id.description_text_view,R.id.price_text_view,R.id.type_text_view};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item_layout,cursor,columnNames,layoutIds,0);

        ListView listView = (ListView) findViewById(R.id.shopping_list_view);
        listView.setAdapter(simpleCursorAdapter);
    }
}
