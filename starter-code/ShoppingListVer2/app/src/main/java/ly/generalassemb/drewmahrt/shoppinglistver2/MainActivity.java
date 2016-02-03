package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

        ListView shoppingListView = (ListView) findViewById(R.id.shopping_list_view);

        ShoppingSQLiteOpenHelper helper = new ShoppingSQLiteOpenHelper(MainActivity.this, "SHOPPING_LIST", null, 0);

        Cursor cursor = helper.getShoppingList();
        Log.d("Cursor Count", cursor.getCount()+"");

        CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView textView = (TextView)view.findViewById(android.R.id.text1);
                String itemName = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));

                textView.setText(itemName);

            }
        };

        shoppingListView.setAdapter(cursorAdapter);

    }
}
