package ly.generalassemb.drewmahrt.shoppinglistver2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ly.generalassemb.drewmahrt.shoppinglistver2.models.GroceryItem;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class GrocerySQLHelper extends SQLiteOpenHelper {

  private static final String TAG = GrocerySQLHelper.class.getCanonicalName();
  private static GrocerySQLHelper INSTANCE;

  public static final String TABLE_NAME = "SHOPPING_LIST";
  public static final String COL_ID = "_id";
  public static final String COL_ITEM_NAME = "ITEM_NAME";
  public static final String COL_DESCRIPTION = "DESCRIPTION";
  public static final String COL_PRICE = "PRICE";
  public static final String COL_TYPE = "TYPE";

  private static final String CREATE_TABLE =
      "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COL_ID +
      " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM_NAME + " TEXT, " +
      COL_DESCRIPTION + ", " + COL_PRICE + " TEXT, " + COL_TYPE + " TEXT )";

  private GrocerySQLHelper(Context context) {
    super(context, DBAssetHelper.DATABASE_NAME, null,
          DBAssetHelper.DATABASE_VERSION);
  }

  public static GrocerySQLHelper getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new GrocerySQLHelper(context);
    }
    return INSTANCE;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.d(TAG, "Creating the table");
    db.execSQL(CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
    onCreate(db);
  }

  public long create(GroceryItem item) {
    SQLiteDatabase db = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COL_ITEM_NAME, item.getName());
    values.put(COL_DESCRIPTION, item.getDescription());
    values.put(COL_PRICE, item.getPrice());
    values.put(COL_TYPE, item.getItemType().name());
    return db.insert(TABLE_NAME, null, values);
  }

  public Cursor getGroceries() {
    SQLiteDatabase db = getReadableDatabase();

    Log.d(TAG, "Querying the groceries");
    return db.query(TABLE_NAME, null, null, null, null, null, null);
  }

  public void deleteById(String id) {
    SQLiteDatabase db = getWritableDatabase();
    String whereClause = COL_ID + " = ?";
    String[] whereArgs = {id};
    db.delete(TABLE_NAME, whereClause, whereArgs);
  }
}