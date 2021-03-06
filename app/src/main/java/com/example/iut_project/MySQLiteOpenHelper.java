package com.example.iut_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Contact.db";
    public static final String TABLE_NAME = "contact_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "TEL";
    public static final String ID = "" ;



    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,TEL INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String surname,String tel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,tel);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String name,String surname,String tel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,tel);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Cursor getOneData(int id){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_1 + "=" + id , null);
        return cursor;

        }

    public List<AllContact> readContact(){
            List<AllContact> contacts = new ArrayList<>();

            String strSql = "select * from " + TABLE_NAME;
            Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
            cursor.moveToFirst();
             while (!cursor.isAfterLast()){
            AllContact contact = new AllContact(cursor.getInt(0) , cursor.getString(1),
                    cursor.getString(2), cursor.getString(3) );
            contacts.add(contact);
            cursor.moveToNext();
        }
             cursor.close();

        return contacts;
        }

    }

