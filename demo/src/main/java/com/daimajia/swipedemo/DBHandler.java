package com.daimajia.swipedemo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by furmon on 08/09/2016.
 */


public class DBHandler extends SQLiteOpenHelper{
    //Version base de donnée
    public static final int DATABASE_VERSION=1;

    //Nom base de donnée
    public static final String DATABASE_NAME="ContactPertinent";



    //Nom table Contact
    public static final String TABLE_CONTACTS="contacts";
    //Nom Colonnes de la table contacts
    public static final String KEY_CONTACT_ID="id_contact_db";
    public static final String KEY_CONTACT_NAME="name";
    public static final String KEY_CONTACT_PHONE="phone";
    public static final String KEY_CONTACT_CONTACTID="contactid";


    //Nom table SMS/MMS
    public static final String TABLE_SMSMMS="smsmms";
    //Nom Colonnes de la table contacts
    public static final String KEY_SMSMMS_ID="id_smsmms_db";
    public static final String KEY_SMSMMS_ORIGINE="origine";
    public static final String KEY_SMSMMS_CONTACTID="contactid";
    public static final String KEY_SMSMMS_DATE="date";


    //Nom table Appel
    public static final String TABLE_APPEL="appel";
    //Nom Colonnes de la table contacts
    public static final String KEY_APPEL_ID="id_contact_db";
    public static final String KEY_APPEL_CONTACTID="contactid";
    public static final String KEY_APPEL_ORIGINE="origine";
    public static final String KEY_APPEL_DUREE="duree";
    public static final String KEY_APPEL_DATE="date";




    public DBHandler(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try {
            //Création table contact
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + " ("
                    + KEY_CONTACT_ID + " INTEGER PRIMARY KEY, " + KEY_CONTACT_NAME + " TEXT," + KEY_CONTACT_PHONE
                    + " TEXT, " + KEY_CONTACT_CONTACTID + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);

            //Création table sms/mms
            String CREATE_SMSMMS_TABLE = "CREATE TABLE " + TABLE_SMSMMS + " ("
                    + KEY_SMSMMS_ID + " INTEGER PRIMARY KEY," + KEY_SMSMMS_ORIGINE + " TEXT, " +
                    KEY_SMSMMS_CONTACTID + " TEXT" + ", " + KEY_SMSMMS_DATE + " TEXT" + " )";
            db.execSQL(CREATE_CONTACTS_TABLE);


            //Création table appel
            String CREATE_APPEL_TABLE = "CREATE TABLE " + TABLE_APPEL + " ("
                    + KEY_APPEL_ID + " INTEGER PRIMARY KEY, " + KEY_APPEL_CONTACTID + " TEXT, " +
                    KEY_APPEL_ORIGINE + " TEXT ," + KEY_APPEL_DUREE + " TEXT ," + KEY_SMSMMS_DATE + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Drop ancienne table si elle existe
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTS);
        onCreate(db);
    }


    //Insertion dans la Base de donnée

    //Inserer nouveau contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTACT_NAME, contact.getFirstName());
        values.put(KEY_CONTACT_PHONE, contact.getPhoneNumber());
        values.put(KEY_CONTACT_CONTACTID, contact.getContactId());
        //Ajout de la colonne
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    //Inserer nouveau sms/mms
    public void addSMSMMS(Smsmms smsmms){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SMSMMS_ORIGINE, smsmms.getOrigine());
        values.put(KEY_SMSMMS_CONTACTID, smsmms.getContactId());
        values.put(KEY_SMSMMS_DATE, smsmms.getDate());
        //Ajout de la colonne
        db.insert(TABLE_SMSMMS, null, values);
        db.close();
    }


    //Inserer nouvel appel
    public void addAppel(Appel appel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_APPEL_ORIGINE, appel.getOrigine());
        values.put(KEY_APPEL_CONTACTID, appel.getContactId());
        values.put(KEY_APPEL_DUREE, appel.getDuree());
        values.put(KEY_APPEL_DATE, appel.getDate());

        //Ajout de la colonne
        db.insert(TABLE_APPEL, null, values);
        db.close();
    }


    //Modifier contact

    public void modifyContact(Contact newContact, Contact oldContact){
        deleteContact(oldContact);
        addContact(newContact);
    }






    //Retirer information de la Base de donnée


    //Obtenir la list des contacts
    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT*FROM "+TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //boucle pour remplir l'arraylist
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setDbId(cursor.getString(0));
                contact.setFirstName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.setContactId(cursor.getString(3));

                //Ajout du contact à la liste
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;

    }

    //Supprimer contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,KEY_CONTACT_ID+"=?",
                new String[]{String.valueOf(contact.getDbId())});
        db.close();
    }

}