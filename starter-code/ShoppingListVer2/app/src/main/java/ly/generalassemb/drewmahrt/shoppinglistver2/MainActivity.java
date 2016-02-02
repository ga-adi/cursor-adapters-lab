package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        helper = new DatabaseHelper(MainActivity.this);

        final Cursor cursor = helper.getAllShoppingList();
        final CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.icon_list_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView nametextView = (TextView) view.findViewById(R.id.icon_name_text_view);
                String cursorName = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));
                String cursorDescription = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                TextView priceTextView = (TextView)view.findViewById(R.id.price_text_view);
                String cursorPrice = cursor.getString(cursor.getColumnIndex("PRICE"));

                nametextView.setText(cursorName);
                priceTextView.setText(cursorPrice);


            }
        };

        final ListView listView = (ListView)findViewById(R.id.shopping_list_view);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nametextView = (TextView) view.findViewById(R.id.icon_name_text_view);
                String cursorDescription = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                String cursorName = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));
                if (nametextView.getText().toString().equals(cursorDescription)) {
                    nametextView.setText(cursorName);
                } else if (nametextView.getText().toString().equals(cursorName)) {
                    nametextView.setText(cursorDescription);
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nametextView = (TextView) view.findViewById(R.id.icon_name_text_view);
                String cursorDescription = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                String cursorName = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));
                helper.deleteFromShoppingList(nametextView.getText().toString());
                cursorAdapter.swapCursor(helper.getAllShoppingList());
                return true;


            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        mClient.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://ly.generalassemb.drewmahrt.shoppinglistver2/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(mClient, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://ly.generalassemb.drewmahrt.shoppinglistver2/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(mClient, viewAction);
//        mClient.disconnect();
//    }
}
