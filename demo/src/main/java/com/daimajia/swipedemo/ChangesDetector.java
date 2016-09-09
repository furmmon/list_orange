package com.daimajia.swipedemo;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vincenthoulbreque on 09/09/16.
 *
 * Analyse les changements qui ont lieu dans les contacts
 * entre l'application Contacts et la BdD créée
 */


public class ChangesDetector {

    public static ArrayList<Contact> getContactsFromPhoneDatabase(Context context) {
        //Adresse de la table dans la base de donnée
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        //Récupération des données
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.RawContacts.CONTACT_ID};

        //Initialisation du curseur
        Cursor people = context.getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int indexId = people.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID);

        ArrayList<Contact> contacts = new ArrayList<Contact>();

        //Mise en forme des données
        people.moveToFirst();
        do {
            String name   = people.getString(indexName);
            String number = people.getString(indexNumber);
            String contactid = people.getString(indexId);

            Contact contact1 = new Contact();

            contact1.setFirstName(name);
            contact1.setPhoneNumber(number);
            contact1.setContactId(contactid);


            contacts.add(contact1);

        } while (people.moveToNext());

        return contacts;
    }

    /*
     * Retourne la liste des contacts de la BdD créée par l'application ORANGE
     */
    public static ArrayList<Contact> getContactsFromApplicationDatabase(Context context){
        DBHandler dbHandler = new DBHandler(context);

        ArrayList<Contact> contactsFromApplicationDatabase = dbHandler.getAllContacts();

        return contactsFromApplicationDatabase;
    }

    /*
     * Retourne la liste des contacts de la BdD créée par l'application ORANGE
     */
    public static boolean compareAndUpdateDatabases(Context context, ArrayList<Contact> contactsFromApplicationContact, ArrayList<Contact> contactsFromApplication){
        /**
         * @param context : context de l'activité
         * @param contactsFromApplicationContact : liste des contacts issue de la BdD de l'application Contacts
         * @param : contactsFromApplication : liste des contacts issue de la BdD de l'application ORANGE
         * @return : true si il y a eu un update de la BdD, false sinon
         */

        ArrayList<Contact> contactsUpdated; // Contacts de l'application Contacts qui ont été modifiés
        ArrayList<Contact> contactsCreated; // Contacts de l'application Contacts qui ont été créés
        ArrayList<Contact> contactsDeleted; // Contacts de l'application Contacts qui ont été supprimés

        // Contacts de la BdD de Contacts
        /*protected String firstName;
        protected String phoneNumber;
        protected String contactId;
        protected String dbId;*/


        // Contacts de la BdD d'ORANGE
        /*public static final String KEY_CONTACT_ID="id_contact_db";
        public static final String KEY_CONTACT_NAME="name";
        public static final String KEY_CONTACT_PHONE="phone";
        public static final String KEY_CONTACT_CONTACTID="contactid";*/

        for(int i = 0; i<contactsFromApplicationContact.size(); i++){

        }





    }


    public static boolean updateApplicationDatabase(Context context){

        // Les contacts de la BdD de l'application Contacts
        ArrayList<Contact> contactsFromPhoneDatabase = getContactsFromPhoneDatabase(context);

        // Les contacts de la BdD de l'application Orange
        ArrayList<Contact> contactsFromApplicationDatabase = getContactsFromApplicationDatabase(context);

        // Comparer les 2 listes de contacts et update la liste des contacts de la BdD de ORANGE



    }


}
