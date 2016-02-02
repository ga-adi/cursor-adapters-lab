package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mDatabaseHelper = new DatabaseHelper(MainActivity.this);



        //Cursor adapter
        Cursor cursor = mDatabaseHelper.getEntireList();

        final CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this,cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.shopping_list_item,parent,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView nameTextView = (TextView)view.findViewById(R.id.shopping_list_name_textView);
                TextView descriptinTextView = (TextView)view.findViewById(R.id.shopping_list_description_textView);
                TextView priceTextView = (TextView)view.findViewById(R.id.shopping_list_price_textView);
                String itemName = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));
                String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                String price = cursor.getString(cursor.getColumnIndex("PRICE"));

                nameTextView.setText(itemName);
                descriptinTextView.setText("("+description+")");
                priceTextView.setText(price);

            }
        };

        ListView shoppingListView = (ListView)findViewById(R.id.shopping_list_view);
        shoppingListView.setAdapter(cursorAdapter);

        shoppingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textView = (TextView)view.findViewById(R.id.shopping_list_name_textView);
                String itemName = textView.getText().toString();
                Toast.makeText(MainActivity.this,"DELETING THE ITEM "+itemName,Toast.LENGTH_SHORT).show();
              
                mDatabaseHelper.deleteItem(itemName);
                Cursor newCursor = mDatabaseHelper.getEntireList();
                cursorAdapter.swapCursor(newCursor);
                return true;
            }
        });
    }



}
