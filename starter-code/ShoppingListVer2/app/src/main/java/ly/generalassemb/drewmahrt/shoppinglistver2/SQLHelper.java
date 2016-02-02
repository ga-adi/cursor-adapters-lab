package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;

/**
 * Created by YPsitos on 2/2/16.
 */
public class SQLHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String TABLE_NAME = "SHOPPING_LIST";
    public static final String COLUMN_ID = "_id";
    public static final String[] ICON_COLUMNS = {COLUMN_ID,"ITEM_NAME","DESCRIPTION","PRICE","TYPE"};
    private static final String CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "ITEM_NAME TEXT, " +
                    "DESCRIPTION TEXT, PRICE INTEGER, TYPE TEXT)";


    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPPING_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        this.onCreate(db);
    }

    public Cursor getItemList(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);


        return cursor;
    }
}
