package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ShowMe on 2/2/16.
 */
public class GrocerySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = GrocerySQLiteOpenHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String GROCERY_LIST_TABLE_NAME = "SHOPPING_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_ITEM_DESCRIPTION = "DESCRIPTION";
    public static final String COL_PRICE = "PRICE";
    public static final String COL_TYPE = "TYPE";

    public static final String[] GROCERY_COLUMNS = {COL_ID, COL_ITEM_NAME, COL_ITEM_DESCRIPTION, COL_PRICE, COL_TYPE};

    private static final String CREATE_GROCERY_LIST_TABLE = "CREATE TABLE"
            + GROCERY_LIST_TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ITEM_NAME + " TEXT, " +
            COL_ITEM_DESCRIPTION + " TEXT, " +
            COL_PRICE + " TEXT, " +
            COL_TYPE + " TEXT )";


    public GrocerySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GROCERY_LIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GROCERY_LIST_TABLE_NAME);
        this.onCreate(db);
    }

    public Cursor getGroceryList() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GROCERY_LIST_TABLE_NAME, GROCERY_COLUMNS, null, null, null, null, null, null);
        return cursor;
    }

    public void removeItem(String  name){
        // Define 'where' part of query.
        String selection = "ITEM_NAME=  ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { name };
//        Log.d("BLAH", "Blah"+name);
// Issue SQL statement.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("SHOPPING_LIST", selection, selectionArgs);
    }

}
