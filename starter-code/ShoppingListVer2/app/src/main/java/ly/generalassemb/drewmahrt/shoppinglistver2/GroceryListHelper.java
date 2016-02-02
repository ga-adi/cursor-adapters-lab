package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by sbabba on 2/2/16.
 */
public class GroceryListHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String SHOPPING_TABLE_NAME = "SHOPPING_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_PRICE = "PRICE";
    public static final String COL_TYPE = "TYPE";

    public static final String[] GROCERY_COLUMNS = {COL_ID,COL_ITEM_NAME,COL_DESCRIPTION,COL_PRICE,COL_TYPE};

    public static final String CREATE_GROCERY_LIST_TABLE =
            "CREATE TABLE" + SHOPPING_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ITEM_NAME + " TEXT, " +
                    COL_DESCRIPTION + " TEXT, " +
                    COL_PRICE + " TEXT, " +
                    COL_TYPE + " TEXT )";


    public GroceryListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GROCERY_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_TABLE_NAME);
        this.onCreate(db);
    }

    public Cursor getGroceryList(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOPPING_TABLE_NAME, // a. table
                GROCERY_COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        return cursor;
    }

    public void removeItems(String name) {
        String selection = "ITEM_NAME = ?";

        String[] selectionArgs = {name};

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("SHOPPING_LIST", selection,selectionArgs);

    }
}

