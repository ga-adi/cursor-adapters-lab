package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by charlie on 2/2/16.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String SQL_CREATE_SHOPPING_LIST_TABLE =
            "CREATE IF NOT EXISTS SHOPPING_LIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, ITEM_NAME TEXT, DESCRIPTION TEXT, PRICE TEXT, TYPE TEXT)";
    public static final String SQL_DROP_SHOPPING_LIST_TABLE =
            "DROP TABLE IF EXISTS SHOPPING_LIST";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SHOPPING_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_SHOPPING_LIST_TABLE);
        onCreate(db);
    }

    public Cursor getAllGroceryItems() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("SHOPPING_LIST", null, null, null, null, null, null);
    }

    public GroceryItem getGrocerItem(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                "SHOPPING_LIST",    // table
                null,               // columns (null = *)
                "_id = ?",          // selection: WHERE _id = ?
                new String[]{String.valueOf(id)}, // selectionArgs: WHERE _id = id
                null,               // group by
                null,               // having
                null                // order by
        );
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        } else {
            String itemName = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));
            String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
            String price = cursor.getString(cursor.getColumnIndex("PRICE"));
            String type = cursor.getString(cursor.getColumnIndex("TYPE"));
            cursor.close();
            return new GroceryItem(id, itemName, description, price, type);
        }
    }

    public void deleteGroceryItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM SHOPPING_LIST WHERE _id = " + id);
    }

    public void insert(String name, String description, String price, String type) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ITEM_NAME", name);
        values.put("DESCRIPTION", description);
        values.put("PRICE", price);
        values.put("TYPE", type);
        db.insert("SHOPPING_LIST", null, values);
    }

    public void initializeDataForTesting() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM SHOPPING_LIST");
        insert("Bread", "Whole Wheat Bread", "2.35", "Food");
        insert("Milk", "1 Gallon Skim Milk", "3.50", "Dairy");
        insert("Ice Cream", "Mint Chocolate Chip", "2.20", "Dairy");
        insert("Paper Plates", "White Paper Plates", "7.50", "Dishes");
        insert("Chicken Breasts", "Boneless Skinless", "2.30", "Poultry");
        insert("Carrots", "Baby Carrots", "4.00", "Produce");
        insert("Lettuce", "Iceberg", "3.14", "Produce");
    }
}
