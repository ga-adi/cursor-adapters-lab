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
import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBSQLiteOpenHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        DBSQLiteOpenHelper helper = new DBSQLiteOpenHelper(MainActivity.this);
        Cursor cursor = helper.getExampleList();

        CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.shopping_list, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                TextView textViewItemName = (TextView) view.findViewById(R.id.textViewItemName);
                TextView textViewItemPrice = (TextView) view.findViewById(R.id.textViewItemPrice);

                String itemName = cursor.getString(cursor.getColumnIndex(DBSQLiteOpenHelper.COL_ITEM_NAME));
                String itemPrice = cursor.getString(cursor.getColumnIndex(DBSQLiteOpenHelper.COL_PRICE));

                textViewItemName.setText(itemName);
                textViewItemPrice.setText("$ " +itemPrice);

            }
        };

        ListView listView = (ListView) findViewById(R.id.shopping_list_view);
        listView.setAdapter(cursorAdapter);

        /*AdapterView.OnLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String removeItem = ((TextView)view.findViewById(R.id.textViewItemName)).getText().toString();
                return true;
            }
        }*/

    }
}
