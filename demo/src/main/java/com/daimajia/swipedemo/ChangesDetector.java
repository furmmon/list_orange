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
 * Observateur : il va écouter les changements qui ont lieu dans les contacts
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


}
