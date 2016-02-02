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

import org.w3c.dom.Text;

import java.util.ArrayList;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    GroceryListHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mHelper = new GroceryListHelper(MainActivity.this);
        Cursor cursor = mHelper.getGroceryList();

        final CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.description_text_view);
                TextView priceTextView = (TextView) view.findViewById(R.id.price_text_view);
                TextView typeTextView = (TextView) view.findViewById(R.id.type_text_view);


                String item_name = cursor.getString(cursor.getColumnIndex(GroceryListHelper.COL_ITEM_NAME));
                String description = cursor.getString(cursor.getColumnIndex(GroceryListHelper.COL_DESCRIPTION));
                String price = cursor.getString(cursor.getColumnIndex(GroceryListHelper.COL_PRICE));
                String type = cursor.getString(cursor.getColumnIndex(GroceryListHelper.COL_TYPE));

                nameTextView.setText(item_name);
                descriptionTextView.setText(description);
                priceTextView.setText(price);
                typeTextView.setText(type);

            }
        };

        ListView listView = (ListView) findViewById(R.id.shopping_list_view);
        listView.setAdapter(cursorAdapter);


        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String itemNameRemove = ((TextView)view.findViewById(R.id.name_text_view)).getText().toString();

                mHelper.removeItems(itemNameRemove);
                Cursor cursorInside = mHelper.getGroceryList();
                cursorAdapter.swapCursor(cursorInside);
                return true;
            }
        };
        listView.setOnItemLongClickListener(longClickListener);
    }
}

