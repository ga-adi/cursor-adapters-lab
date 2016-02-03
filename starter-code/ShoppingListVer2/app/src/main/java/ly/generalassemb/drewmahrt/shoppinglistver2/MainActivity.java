package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShoppingList = (ListView) findViewById(R.id.shopping_list_view);

        //Ignore the two lines below, they are for setup
        final DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        final NewOpenHelper helper = new NewOpenHelper(MainActivity.this);

        final Cursor cursor =helper.getList();

        final CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this,cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView textView = (TextView)view.findViewById(android.R.id.text1);
                String name = cursor.getString(1);
                textView.setText(name);
            }
        };
        mShoppingList.setAdapter(cursorAdapter);

        final AdapterView.OnItemClickListener remove = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
                helper.deleteRow(item);
                cursorAdapter.changeCursor(cursor);
                cursorAdapter.notifyDataSetChanged();
            }
        };
        mShoppingList.setOnItemClickListener(remove);


    }
}
