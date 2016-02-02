package ly.generalassemb.drewmahrt.shoppinglistver2.setup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wasabi on 2/2/2016.
 */
public class ShoppingSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String TABLE_NAME = "SHOPPING_LIST";

    public static final String ITEM_ID = "_id";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_DESCRIPTION = "DESCRIPTION";
    public static final String ITEM_PRICE = "PRICE";
    public static final String ITEM_TYPE = "TYPE";

    public static final String[] COLUMN_NAMES = new String[]{ITEM_ID, ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_TYPE};


    public ShoppingSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(" +
                ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM_NAME + " TEXT, " +
                ITEM_DESCRIPTION + " TEXT, " +
                ITEM_PRICE + " TEXT, " +
                ITEM_TYPE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        this.onCreate(db);
    }

    public Cursor getShoppingList(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                COLUMN_NAMES,
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    public void deleteRow(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] itemIds = new String[]{ String.valueOf(id) };
        db.delete(TABLE_NAME, null, itemIds);
    }
}
