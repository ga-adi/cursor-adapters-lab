package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gregorydaly on 2/2/16.
 */
public class NewOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SHOPPING_DB";
    private static final int DATABASE_VERSION = 7;


    public static final String SHOPPING_LIST = "SHOPPING_LIST";
    public static final String[] COLUMNS = {"_id","ITEM_NAME","DESCRIPTION","PRICE","TYPE"};

    public NewOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor getList(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOPPING_LIST,COLUMNS,null,null,null,null,null,null);
        return cursor;
    }

    public void deleteRow(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SHOPPING_LIST, "ITEM_NAME LIKE ?", new String[]{name});
        db.close();
    }
}
