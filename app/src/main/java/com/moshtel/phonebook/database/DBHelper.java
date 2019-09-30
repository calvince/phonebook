package com.moshtel.phonebook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="contact.db";
    public static final String CONTACT_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID= "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE ="phone";
    private HashMap hp;

    public  DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table contacts " +"(id integer primary key autoincrement,name text,phone text,email text,street text,place text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(sqLiteDatabase);
    }
    public boolean insertContact (String  name,String phone,String email,String street,String place){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        contentValues.put("street",street);
        contentValues.put("place",place);
        sqLiteDatabase.insert("contacts",null,contentValues);
        return true;
    }
    public Cursor getData(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from contacts where id = "+id+"",null);
        return cursor;
    }
    public int numberOfRows(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase,CONTACT_TABLE_NAME);
        return numRows;
    }
    public boolean updateContact (Integer id,String name,String phone,String email,String street,String place){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        sqLiteDatabase.update("contacts",contentValues,"id = ? ",new String[]{Integer.toString(id)});
        return true;
    }
    public Integer deleteContact(Integer id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("contacts",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }
    public ArrayList<String> getAllContacts(){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from contacts",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            arrayList.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME)));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
