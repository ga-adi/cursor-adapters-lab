package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by faraz on 2/2/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "SHOPPING_LIST";
    public static final String ID = "_id";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRICE = "PRICE";
    public static final String TYPE = "TYPE";
    public static final String TEXT = "TEXT";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY, " +
                ITEM_NAME + " " +
                TEXT + ", " +
                DESCRIPTION + " " +
                TEXT + ", " +
                PRICE + " " +
                TEXT + ", " +
                TYPE + " " +
                TEXT + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(int id, String itemName, String description, String price, String type) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('" + id + "', '" + itemName + "', '" + description + "', '" + price + "', '" + type + "')");
    }

    public Cursor getValues() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
