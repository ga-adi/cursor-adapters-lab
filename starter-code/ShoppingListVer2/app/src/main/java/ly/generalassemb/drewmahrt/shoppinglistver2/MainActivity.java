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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    CursorAdapter mAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        SQLHelper helper = new SQLHelper(MainActivity.this);
        Cursor cursor = helper.getItemList();
        Log.d("TAG", "Cursor count :" + cursor.getCount());

        mListView = (ListView)findViewById(R.id.shopping_list_view);

        mAdapt = new CursorAdapter(MainActivity.this,cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.activity_list_item,parent,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                String itemName = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));
                TextView nameView = (TextView)view.findViewById(R.id.name_text_view);
                nameView.setText(itemName);
            }
        };
        mListView.setAdapter(mAdapt);




    }
}
