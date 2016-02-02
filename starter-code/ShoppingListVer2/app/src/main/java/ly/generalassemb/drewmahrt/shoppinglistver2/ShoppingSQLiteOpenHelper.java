package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maratmamin on 2/2/16.
 */
public class ShoppingSQLiteOpenHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";


    public ShoppingSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SHOPPING_LIST (_id INTEGER, ITEM_NAME TEXT, DESCRIPTION TEXT, PRICE TEXT, TYPE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SHOPPING_LIST");
        this.onCreate(db);


    }

    public Cursor getShoppingList(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor shopCursor = db.query("SHOPPING_LIST",
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        return shopCursor;
    }


    //
//    public static final String COL_ID = "_id";
//    public static final String COL_ITEM_NAME = "ITEM_NAME";
//
//    public static final String[] SHOPPING_COLUMNS = {COL_ID, COL_ITEM_NAME};
//
//    private static final String CREATE_SHOPPING_LIST_TABLE =
//            "CREATE TABLE " + SHOPPING_LIST_TABLE_NAME +
//                    "(" +
//                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COL_ITEM_NAME + " TEXT )";
//
//
//    private static ShoppingSQLiteOpenHelper instance;
//
//    public static ShoppingSQLiteOpenHelper getInstance(Context context){
//        if(instance == null){
//            instance = new ShoppingSQLiteOpenHelper(context);
//        }
//        return instance;
//    }
//
//    private ShoppingSQLiteOpenHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_SHOPPING_LIST_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_LIST_TABLE_NAME);
//        this.onCreate(db);
//    }
//

}
