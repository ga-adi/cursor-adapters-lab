package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Todo on 2/2/2016.
 */
public class ShoppingSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SHOPPING_DB";
    private static final int DATABASE_VERSION = 7;
    public static final String TABLE_NAME = "SHOPPING_LIST";
    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_PRICE = "PRICE";
    public static final String COL_TYPE = "TYPE";

    public static final String[] ICON_COLUMNS = {COL_ID,COL_ITEM_NAME,COL_DESCRIPTION,COL_PRICE,COL_TYPE};

    private static final String CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " +TABLE_NAME+ " ( " +
            COL_ID+ " INTEGER PRIMARY KEY, " +
            COL_ITEM_NAME+ " TEXT, " +
            COL_DESCRIPTION+ " TEXT, " +
            COL_PRICE+ " INTEGER, " +
            COL_TYPE+ " TEXT)";

    public ShoppingSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPPING_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void insert(int id, String itemName, String desc, double price, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("itemName",itemName);
        contentValues.put("description",desc);
        contentValues.put("price",+ price);
        contentValues.put("type", type);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getShoppingList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, ICON_COLUMNS, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor remove(int position){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, ICON_COLUMNS, null, null, null, null, null, null);
        cursor.moveToPosition(position);
        int idToDelete = cursor.getInt(cursor.getColumnIndex(COL_ID));
        String selection = COL_ID + " = ?";
        String[] itemIds = new String[]{ String.valueOf(idToDelete) };
        db.delete(TABLE_NAME, selection, itemIds);
        cursor = db.query(TABLE_NAME, ICON_COLUMNS, null, null, null, null, null, null);
        return cursor;
    }
}
