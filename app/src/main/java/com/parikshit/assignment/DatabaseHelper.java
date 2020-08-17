package com.parikshit.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final String TABLE_NAME = "user_table";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "Name";
    public static final String Col_3 = "Age";
    public static final String Col_4 = "Checked";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("create table user_table(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Age INTEGER,Checked INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP table if exists user_table");
onCreate(db);
    }

    public boolean insertData(String name,int age,int checked){
        String Age = Integer.toString(age);
        String Checked = Integer.toString(checked);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Age",Age);
        contentValues.put("Checked",Checked);

        long result = db.insert("user_table",null,contentValues);
        if (result==-1){
            return false;
        }
        else {return true;}
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user_table ",null);
        return res;
    }

    public int deleteDATA(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Id = Integer.toString(id);
        return db.delete("user_table","ID = ? ",new String[]{Id});
    }

    public boolean UpdateChecked(String id,String name,String Age,String Checked){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Age",Age);
        contentValues.put("Checked",Checked);
        db.update(TABLE_NAME,contentValues,"ID = ? ",new String[] {id});
return true;
    }

}
