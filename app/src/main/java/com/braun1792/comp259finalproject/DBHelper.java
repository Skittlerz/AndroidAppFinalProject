package com.braun1792.comp259finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by braun1792 on 2/16/2017.
 */
public class DBHelper extends SQLiteOpenHelper {
    //Define Database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contact_list";
    private static final String DATABASE_TABLE = "contact";
    //Define Columns
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";

    private static int taskCount;

    DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String sqlStatement = "CREATE TABLE "+ DATABASE_TABLE + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, "
                + KEY_PHONE + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_ADDRESS + " TEXT)";
        database.execSQL(sqlStatement);
        taskCount = 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " +  DATABASE_TABLE);
    }

    //************* DATABASE OPERATIONS ********************

    public void addContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //increment task count, which acts as the primary key id
        taskCount++;
        //add info for db entry
        values.put(KEY_ID,taskCount);
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());

        db.insert(DATABASE_TABLE,null,values);
        db.close();
    }

    public void editContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //add info for db entry
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());

        db.update(DATABASE_TABLE,values,KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())
                });
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_ID,KEY_NAME,KEY_PHONE,KEY_EMAIL,KEY_ADDRESS},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Contact contact = new Contact(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        db.close();
        return contact;
    }

    public void deleteContact(Contact contact){

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(DATABASE_TABLE, KEY_ID + " =?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public int getTaskCount(){
        return taskCount;
    }

    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> contactList = new ArrayList<>();
        String queryList = "SELECT _id,name,phone_number,email,address FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryList,null);

        if (cursor.moveToFirst()){
            do{
                Contact c = new Contact();
                c.setId(cursor.getInt(0));
                c.setName(cursor.getString(1));
                c.setPhone(cursor.getString(2));
                c.setEmail(cursor.getString(3));
                c.setAddress(cursor.getString(4));

                contactList.add(c);
            }while(cursor.moveToNext());
        }

        db.close();
        return contactList;

    }
}
