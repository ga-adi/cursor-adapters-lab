package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;
import ly.generalassemb.drewmahrt.shoppinglistver2.setup.ShoppingSQLiteOpenHelper;

public class MainActivity extends AppCompatActivity {

    ShoppingSQLiteOpenHelper helper;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        helper = new ShoppingSQLiteOpenHelper(MainActivity.this);
        cursor = helper.getShoppingList();


        String[] colomnNames = ShoppingSQLiteOpenHelper.COLUMN_NAMES;
        //String[] colomnNames = new String[]{ShoppingSQLiteOpenHelper.ITEM_NAME};

        int[] layoutIds = new int[]{ R.id.item_id_textview, R.id.item_name_textview, R.id.item_description_textview, R.id.item_price_textview, R.id.item_type_textview};

        simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.shopping_list_item, cursor, colomnNames, layoutIds, 0);

        ListView listView = (ListView)findViewById(R.id.shopping_list_view);
        listView.setAdapter(simpleCursorAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                helper.deleteRow(position);
                simpleCursorAdapter.swapCursor(helper.getShoppingList());
                return true;
            }
        });

    }
}
