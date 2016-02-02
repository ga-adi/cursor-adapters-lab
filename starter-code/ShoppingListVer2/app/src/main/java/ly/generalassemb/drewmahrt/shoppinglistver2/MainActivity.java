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

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView shoppingListView = (ListView)findViewById(R.id.shopping_list_view);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();



        myOpenHelper helper = new myOpenHelper(MainActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor1 = helper.getCursor();

        String[] columns = new String[]{"ITEM_NAME","DESCRIPTION"};
        int[] layoutValues = new int[]{R.id.textView1, R.id.textView2};

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.my_simply_simple_view, cursor1, columns, layoutValues, 0);
        shoppingListView.setAdapter(cursorAdapter);

        


    }
}
