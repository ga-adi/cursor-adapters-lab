package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by williamtygret on 2/2/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 7;
    public static final String SHOPPING_DB = "SHOPPING_DB";

    public DatabaseHelper(Context context) {
        super(context, SHOPPING_DB, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SHOPPING_LIST (_id INTEGER PRIMARY KEY, item_name TEXT, description TEXT, price DOUBLE, type TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//upgrades the current database
        db.execSQL("DROP TABLE IF EXISTS SHOPPING_LIST");
        onCreate(db);
    }

    public void insert(int id, String name, String description, double price, String type){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("item_name", name);
        values.put("description", description);
        values.put("price", price);
        values.put("type", type);

        db.insert("SHOPPING_LIST", null, values);
    }

    public ShoppingList getShoppingList(int id){
        SQLiteDatabase db = getReadableDatabase();

        //SELECT name, year FROM games
        String [] columns = new String[]{"id", "name", "description", "price", "type"};

        //WHERE id = 1
        String selection = "id = ?";

        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = db.query("SHOPPING_LIST", columns, selection, selectionArgs, null, null, null, null);

        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String description = cursor.getString(cursor.getColumnIndex("description"));
        double price = cursor.getDouble(cursor.getColumnIndex("price"));
        String type = cursor.getString(cursor.getColumnIndex("type"));

        return new ShoppingList(id, name, description, price, type);
    }
    public Cursor getAllShoppingList(){
        SQLiteDatabase db = getReadableDatabase();

        String [] columns = new String[]{"id", "name", "description", "price", "type"};

        Cursor cursor = db.query("SHOPPING_LIST", null, null, null, null, null, null, null);


        return cursor;
    }

    public void deleteFromShoppingList(String itemName){
        String selection = "ITEM_NAME = ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { itemName };
// Issue SQL statement.
        SQLiteDatabase db = getWritableDatabase();
        db.delete("SHOPPING_LIST", selection, selectionArgs);

    }

}
