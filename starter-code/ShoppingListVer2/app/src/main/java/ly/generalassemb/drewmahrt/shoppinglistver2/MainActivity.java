package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.List;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        ListView listView = (ListView) findViewById(R.id.shopping_list_view);
        final DBOpenHelper helper = new DBOpenHelper(MainActivity.this);

        //Initialize the database for testing purposes
        helper.initializeDataForTesting();

        Cursor cursor = helper.getAllGroceryItems();

        final CursorAdapter adapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.shopping_list_item, parent, false);
            }

            @Override
            public void bindView(View view, final Context context, Cursor cursor) {
                TextView itemNameTextView = (TextView) view.findViewById(R.id.item_name);
                TextView itemDescTextView = (TextView) view.findViewById(R.id.item_desc);
                TextView itemPriceTextView = (TextView) view.findViewById(R.id.item_price);
                TextView itemTypeTextView = (TextView) view.findViewById(R.id.item_type);

                String itemName = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));
                String itemDesc = cursor.getString( cursor.getColumnIndex("DESCRIPTION") );
                String itemPrice = cursor.getString( cursor.getColumnIndex("PRICE") );
                String itemType = cursor.getString( cursor.getColumnIndex("TYPE") );

                itemNameTextView.setText(itemName);
                itemDescTextView.setText(itemDesc);
                itemPriceTextView.setText(itemPrice);
                itemTypeTextView.setText(itemType);
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.item_name);
                final String itemName = tv.getText().toString();
                final int itemId = (int) id;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete " + itemName + "?");
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteGroceryItem(itemId);
                        adapter.swapCursor(helper.getAllGroceryItems()); // like adapter.notifyDataSetChanged() - get a new cursor
                        Toast.makeText(MainActivity.this, itemName + " deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }
        });
    }
}
