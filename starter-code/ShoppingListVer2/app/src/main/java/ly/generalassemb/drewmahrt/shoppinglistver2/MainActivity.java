package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {

    Cursor mCursor;
    CursorAdapter mCursorAdapter;
    ListView mListView;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        ShoppingListSQLiteOpenHelper helper = ShoppingListSQLiteOpenHelper.getInstance(MainActivity.this);
        mCursor = helper.getShoppingList();

        mCursorAdapter = new CursorAdapter(MainActivity.this, mCursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                mTextView = (TextView)view.findViewById(R.id.item_text_view);
                String itemName = cursor.getString(cursor.getColumnIndex(ShoppingListSQLiteOpenHelper.COL_ITEM_NAME));

                mTextView.setText(itemName);
            }
        };

        mListView = (ListView) findViewById(R.id.shopping_list_view);
        mListView.setAdapter(mCursorAdapter);

    }
}
