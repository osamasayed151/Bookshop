package com.example.bookshop.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavoriteDB extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
    private ContentValues cv;

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "bookShop";
    private static String TABLE_NAME = "favoriteTable";

    public static  String ID = "id";
    public static  String BOOK_IMAGE = "bookImage";
    public static  String BOOK_NAME = "bookName";
    public static  String BOOK_DES = "bookDes";
    public static  String SALARY = "salary";
    public static  String BEFORE_DISCOUNT = "beforeDiscound";
    public static  String ADD_BASKET = "addBasket";
    public static  String FAVORITE_STATUS = "favoriteStatus";

    // Create the table
    private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +"("
            +ID+" TEXT, "+BOOK_IMAGE+" TEXT, "+BOOK_NAME+" TEXT, "+BOOK_DES+" TEXT, "+ADD_BASKET+" TEXT, "+SALARY+" TEXT, "+BEFORE_DISCOUNT+" Text, "+FAVORITE_STATUS+" TEXT)";

    public FavoriteDB(Context context){
        super(context,DATABASE_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    //create empty table
    public void insertEmpty(){
        sqLiteDatabase = this.getWritableDatabase();
        cv = new ContentValues();
        for (int i=1; i < 11; i++){
            cv.put(ID,i);
            cv.put(ADD_BASKET, "0");
            cv.put(FAVORITE_STATUS, "0");

            sqLiteDatabase.insert(TABLE_NAME,null,cv);
        }
    }

    // create data into database
    public void insertIntoTheDatabase(String id,int image, String bookName, String bookDes, double salary, double before, String addBasket, String fav){
        sqLiteDatabase = getWritableDatabase();
        cv = new ContentValues();
        cv.put(ID, id);
        cv.put(BOOK_IMAGE,image);
        cv.put(BOOK_NAME, bookName);
        cv.put(BOOK_DES, bookDes);
        cv.put(SALARY, salary);
        cv.put(BEFORE_DISCOUNT,before);
        cv.put(ADD_BASKET, addBasket);
        cv.put(FAVORITE_STATUS, fav);


        sqLiteDatabase.insert(TABLE_NAME,null,cv);
        Log.d("FavDB Status - ", BOOK_NAME + ", FavoriteStatus - "+FAVORITE_STATUS+" - . " + ", addBasket - "+ADD_BASKET+ cv);
    }

    // Read all data
    public Cursor readAllData(String id ){
        sqLiteDatabase = this.getReadableDatabase();
        String args[] = {id+""};
        String sql = "select * from "+TABLE_NAME+" where "+ID+ " = ?";
        return sqLiteDatabase.rawQuery(sql,args,null);
    }

    //Remove line from database
    public void removeFavoriteItem(String id){
        sqLiteDatabase = getWritableDatabase();
        String args[] = {id+""};
        String sql = "update "+TABLE_NAME+" set "+FAVORITE_STATUS+" ='0' where "+ID+" = ?";
        sqLiteDatabase.execSQL(sql,args);
        Log.d("remove",id.toString());
    }

    //Remove line from database
    public void removeBasketItem(String id){
        sqLiteDatabase = getWritableDatabase();
        String args[] = {id+""};
        String sql = "update "+TABLE_NAME+" set "+ADD_BASKET+" ='0' where "+ID+" = ?";
        sqLiteDatabase.execSQL(sql,args);
        Log.d("remove",id.toString());
    }

    // select all Favorite list
    public Cursor selectAllFavoriteItem(){
        sqLiteDatabase = getReadableDatabase();
        String sql = "select * from "+TABLE_NAME+" where "+FAVORITE_STATUS+" ='1'";
        return sqLiteDatabase.rawQuery(sql,null,null);
    }

    // select all Basket list
    public Cursor selectAllBasketItem(){
        sqLiteDatabase = getReadableDatabase();
        String sql = "select * from "+TABLE_NAME+" where "+ADD_BASKET+" ='1'";
        return sqLiteDatabase.rawQuery(sql,null,null);
    }
}
