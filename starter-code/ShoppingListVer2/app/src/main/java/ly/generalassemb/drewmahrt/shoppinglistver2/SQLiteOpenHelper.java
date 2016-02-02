package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by austinjones on 2/2/16.
 */
public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper{
    private static final String TAG = SQLiteOpenHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String LIST_TABLE_NAME = "SHOPPING_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_ITEM_DESCRIPTION = "DESCRIPTION";
    public static final String COL_ITEM_PRICE = "PRICE";
    public static final String COL_ITEM_TYPE = "TYPE";

    public static final String[] SHOPPING_COLUMNS = {COL_ID, COL_ITEM_NAME, COL_ITEM_DESCRIPTION, COL_ITEM_PRICE, COL_ITEM_TYPE};

    public static final String CREATE_LIST_TABLE = " CREATE TABLE " +
            LIST_TABLE_NAME + "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ITEM_NAME + " TEXT, " + COL_ITEM_DESCRIPTION + " TEXT, " +
            COL_ITEM_PRICE + " TEXT, " + COL_ITEM_TYPE + " TEXT ) ";

    private static android.database.sqlite.SQLiteOpenHelper instance;

    public static android.database.sqlite.SQLiteOpenHelper getInstance(Context context) {
        if(instance == null){
            instance = new SQLiteOpenHelper(context);
        }
        return instance;
    }

    private SQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + LIST_TABLE_NAME);
        this.onCreate(db);
    }

    public Cursor getShoppingList(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LIST_TABLE_NAME, // a. table
                SHOPPING_COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }
}
