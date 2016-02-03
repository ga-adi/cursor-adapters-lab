package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;
import ly.generalassemb.drewmahrt.shoppinglistver2.setup.ShoppingSQLiteHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        //Link SQL Helper
        //Creating the helper

        //Instantiate ShoppingSQLiteHelper object
        ShoppingSQLiteHelper shoppingHelper = new ShoppingSQLiteHelper(MainActivity.this);

        //get access to that method in the Class
        //*must have matching datatypes "Cursor"
        Cursor cursor = shoppingHelper.cursorOfShoppingList();

        //Create Array "ITEM_NAME" column for Cursor Adapter below
        String[] columnName = new String[]{"ITEM_NAME"};
        //create Integer layoutIds to store View ID of simple_list_item_1 below
        int[] layoutIds = new int[]{android.R.id.text1};

        //BUILD CURSOR ADAPTER
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_1, cursor, columnName, layoutIds, 0);

        ListView listview = (ListView) findViewById(R.id.shopping_list_view);
        listview.setAdapter(cursorAdapter);

    }
}
