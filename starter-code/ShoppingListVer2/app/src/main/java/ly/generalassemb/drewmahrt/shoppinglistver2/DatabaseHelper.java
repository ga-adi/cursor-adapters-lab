package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nat on 2/2/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static String DATABASE_NAME = "SHOPPING_DB";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table create here SHOPPING_LIST
        db.execSQL("CREATE TABLE SHOPPING_LIST(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,DESCRIPTION TEXT NOT NULL,PRICE REAL, TYPE TEXT");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //table upgrade code here
        db.execSQL("DROP TABLE IF EXISTS SHOPPING_DB");
        onCreate(db);
    }


    public Cursor getEntireList() {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("SHOPPING_LIST", null, null, null, null, null, null, null);

        return cursor;

    }

    public void deleteItem(String itemName){
        // Define 'where' part of query.
        String selection = "ITEM_NAME=?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {itemName};
        SQLiteDatabase db = getWritableDatabase();
        // Issue SQL statement.
        db.delete("SHOPPING_LIST", selection, selectionArgs);
    }
}
