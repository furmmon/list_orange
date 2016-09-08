package com.daimajia.swipedemo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by furmon on 08/09/2016.
 */


public class DBHandler extends SQLiteOpenHelper{
    //Version base de donnée
    private static final int DATABASE_VERSION=1;
    //Nom base de donnée
    private static final String DATABASE_NAME="contacts";
    //Nom table
    private static final String TABLE_CONTACTS="contacts";
    //Nom Colonnes de la table contacts
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_PHONE="phone";

    public DBHandler(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CONTACTS_TABLE="CREATE TABLE"+TABLE_CONTACTS+"("
                +KEY_ID+"INTEGER PRIMARY KEY,"+KEY_NAME+"TEXT,"+KEY_PHONE
                +"TEXT"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Drop ancienne table si elle existe
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_CONTACTS);
        onCreate(db);
    }




    //Inserer nouveau contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getFirstName());
        values.put(KEY_PHONE, contact.getPhoneNumber());
        //Ajout de la colonne
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    //Obtenir la list des contacts
    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT*FROM"+TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //boucle pour remplir l'arraylist
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(cursor.getString(0));
                contact.setFirstName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                //Ajout du contact à la liste
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;

    }

    //Supprimer contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,KEY_ID+"=?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

}
