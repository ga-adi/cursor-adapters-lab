package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.app.SearchManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        DatabaseHelper helper = new DatabaseHelper(MainActivity.this, "SHOPPING_DB", null, 9);

        helper.insert(2, "Name", "Description", "100", "Type");

        Cursor cursor = helper.getValues();
        cursor.moveToFirst();

        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.ITEM_NAME, DatabaseHelper.DESCRIPTION,
                DatabaseHelper.PRICE, DatabaseHelper.TYPE};
        int[] intArray = new int[]{android.R.id.text1};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_1, cursor, columns, intArray, 0);

        ListView listView = (ListView) findViewById(R.id.shopping_list_view);
        listView.setAdapter(simpleCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_options_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        return true;
    }
}
