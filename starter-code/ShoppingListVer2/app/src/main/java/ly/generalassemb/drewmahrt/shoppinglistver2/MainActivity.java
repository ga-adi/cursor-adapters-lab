package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {

    GrocerySQLiteOpenHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

         mHelper = new GrocerySQLiteOpenHelper(MainActivity.this);
        final Cursor cursor = mHelper.getGroceryList();

        final CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.grocery_list, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView nameTextView = (TextView) view.findViewById(R.id.textViewItemName);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.textViewDescription);
                TextView priceTextView = (TextView) view.findViewById(R.id.textViewPrice);
                TextView typeTextView = (TextView) view.findViewById(R.id.textViewType);

                String itemName = cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_ITEM_NAME));
                String itemDescription = cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_ITEM_DESCRIPTION));
                String itemPrice = cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_PRICE));
                String itemType = cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_TYPE));

                nameTextView.setText(itemName);
                descriptionTextView.setText(" Description: " + itemDescription);
                priceTextView.setText(" Price: $" + itemPrice);
                typeTextView.setText(" Item Type: " + itemType + "\n");
            }
        };

        final ListView listView = (ListView) findViewById(R.id.listViewShoppingList);
        listView.setAdapter(cursorAdapter);

        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "Pushed position " + position, Toast.LENGTH_SHORT).show();
                String itemNameRemove = ((TextView) view.findViewById(R.id.textViewItemName)).getText().toString();
//                Toast.makeText(MainActivity.this, "Toast name?  " +blah, Toast.LENGTH_SHORT).show();

                mHelper.removeItem(itemNameRemove);
                Cursor cursorInside = mHelper.getGroceryList();
                cursorAdapter.swapCursor(cursorInside);
//                cursorAdapter.notifyDataSetChanged();
                return true;
            }
        };
        listView.setOnItemLongClickListener(longClickListener);

    }
}
