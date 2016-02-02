package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by perrycooperman on 2/2/16.
 */
public class myOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final int VERSION_NAME = 7;


    public myOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SHOPPING_LIST VALUES(_id INT PRIMARY KEY, ITEM_NAME TEXT, DESCRIPTION TEXT, PRICE TEXT, TYPE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SHOPPING_LIST");
        onCreate(db);
    }


    public Cursor getCursor(){
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query("SHOPPING_LIST", null, null,null,null,null,null, null);

        return cursor;

    }
}
