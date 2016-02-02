package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rob on 2/2/16.
 */
public class ShoppingListSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = ShoppingListSQLiteOpenHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String SHOPPING_LIST_TABLE_NAME = "SHOPPING_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_DESC = "DESCRIPTION";
    public static final String COL_PRICE = "PRICE";
    public static final String COL_TYPE = "TYPE";
    public static final String[] SHOPPING_COLUMNS = {COL_ID,COL_ITEM_NAME,COL_DESC, COL_PRICE, COL_TYPE};

    public ShoppingListSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    private static final String CREATE_SHOPPING_LIST_TABLE =
            "CREATE TABLE " + SHOPPING_LIST_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ITEM_NAME + " TEXT, " +
                    COL_DESC + " TEXT, " +
                    COL_PRICE + " TEXT, " +
                    COL_TYPE + " TEXT )";

    private static ShoppingListSQLiteOpenHelper instance;
    public static ShoppingListSQLiteOpenHelper getInstance(Context context) {
        if(instance == null){
            instance = new ShoppingListSQLiteOpenHelper(context);
        }
        return instance;
    }

    public ShoppingListSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPPING_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_LIST_TABLE_NAME);
        this.onCreate(db);
    }

    public Cursor getShoppingList() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOPPING_LIST_TABLE_NAME,
                SHOPPING_COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null );

        return cursor;
    }

}
