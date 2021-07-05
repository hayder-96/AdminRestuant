package app.myapp.restuantadmin.ServerApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqLiteData extends SQLiteOpenHelper {

    public static final String DATA_BASE = "noty";
    public static final int version = 1;
    public static final String TABLE = "notyfi";
    public static final String ID = "id";
    public static final String Name = "name";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ Name + " TEXT );";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;

    public SqLiteData(@Nullable Context context) {
        super(context,DATA_BASE,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
    }





    public long insertData(String name) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Name,name);
        long l= sqLiteDatabase.insert(TABLE, null, contentValues);
        sqLiteDatabase.close();
        return l;
    }




    public void delete() {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE, null, null);
    }











    public ArrayList<String> getData() {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT*FROM " + TABLE, null);
        if (cursor.moveToFirst() && cursor != null) {

            do {


                String name = cursor.getString(cursor.getColumnIndex(Name));


                arrayList.add(name);

            } while (cursor.moveToNext());

            return arrayList;
        }
        return null;
    }












}
