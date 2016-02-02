package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class MainActivity extends AppCompatActivity {

    ListView mShoppingList;
    CursorAdapter mShoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShoppingList = (ListView)findViewById(R.id.shopping_list_view);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        final ShoppingSQLiteOpenHelper helper = new ShoppingSQLiteOpenHelper(MainActivity.this);
        helper.insert(1,"Bread","Whole Wheat Bread",2.35,"Food");
        helper.insert(2,"Milk","1 Gallon Skim Milk",3.50,"Dairy");
        helper.insert(3,"Ice Cream","Mint Chocolate Chip",2.20,"Dairy");
        helper.insert(4,"Paper Plates","White Paper Plates",7.50,"Dishes");
        helper.insert(5,"Chicken Breasts","Boneless Skinless",2.30,"Poultry");
        helper.insert(6,"Carrots","Baby Carrots",4.00,"Produce");
        helper.insert(7,"Lettuce","Iceberg",3.14,"Produce");
        final Cursor cursor = helper.getShoppingList();

        mShoppingListAdapter = new CursorAdapter(MainActivity.this,cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.shopping_list_item,parent,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView id = (TextView)view.findViewById(R.id.xmlShoppingListID);
                TextView name = (TextView)view.findViewById(R.id.xmlShoppingListName);
                TextView desc = (TextView)view.findViewById(R.id.xmlShoppingListDesc);
                TextView price = (TextView)view.findViewById(R.id.xmlShoppingListPrice);
                TextView type = (TextView)view.findViewById(R.id.xmlShoppingListType);

                id.setText(cursor.getString(cursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ID)));
                name.setText(cursor.getString(cursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_NAME)));
                desc.setText(cursor.getString(cursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_DESCRIPTION)));
                price.setText(cursor.getString(cursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_PRICE)));
                type.setText(cursor.getString(cursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_TYPE)));
            }
        };
        mShoppingList.setAdapter(mShoppingListAdapter);

        mShoppingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mShoppingListAdapter.swapCursor(helper.remove(position));
                return true;
            }
        });
    }
}
