package ly.generalassemb.drewmahrt.shoppinglistver2.setup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Envy on 2/2/2016.
 */
public class ShoppingSQLiteHelper extends SQLiteOpenHelper {

    //VERSION is private
    //Specify title names of Table
    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String TABLE_NAME = "SHOPPING_LIST";

    public static final String ID = "_id";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRICE = "PRICE";
    public static final String TYPE = "TYPE";

    public static final String[] COLUMN_NAMES = new String[]{ ID, ITEM_NAME, DESCRIPTION, PRICE, TYPE };

    //Code Generator > CONSTRUCTOR:
    //Delete String Name = Variable declared already
    public ShoppingSQLiteHelper(Context context) {
        //create null, and link DATABASE_NAME, DATABASE_VERSION
        //removed parameter items that wont be used
        //super is a constructor for parent class
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //CREATING DATABASE: CODE GENERATOR > ONCREATE > ONUPGRADE
    //ONCREATE, TO CREATE DATABASE
    @Override
    public void onCreate(SQLiteDatabase db) {
        //DATABASE CREATION
        //execSQL = Takes a string as a parameter
        //db.exeSQL( "CREATE TABLE table_name ( column names DATATYPES)")
        db.execSQL("CREATE TABLE SHOPPING_LIST ( _id INTEGER PRIMARY KEY AUTO ICREMENT, ITEM_NAME TEXT, DESCRIPTION TEXT, PRICE TEXT, TYPE TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade = Database Manipulation to recreate a new database

        //Erase, "drop" old SHOPPING_LIST if exists
        //drop "SHOPPING_LIST"
        db.execSQL("DROP TABLE IF EXISTS SHOPPING_LIST");

        //Then create new 'db' below
        this.onCreate(db);
    }

    //Refer to table where content is located
    //Create method to RETURN a Cursor

    public Cursor cursorOfShoppingList(){

        //Specify which database Cursor will be used
        //.getReadableDatabase will automatically grab database
        SQLiteDatabase databaze = this.getReadableDatabase();

        //Refer the Cursor to new databaze
        //SELECT * FROM SHOPPING_LIST WHERE ITEM_NAME = "Milk";
        Cursor cursor = databaze.query(TABLE_NAME, COLUMN_NAMES,null,null,null,null,null,null);
        
        return cursor;
    }




}
