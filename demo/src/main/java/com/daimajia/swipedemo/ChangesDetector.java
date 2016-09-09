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
import com.daimajia.swipedemo.Contact;

/**
 * Created by vincenthoulbreque on 09/09/16.
 *
 * Analyse les changements qui ont lieu dans les contacts
 * entre l'application Contacts et la BdD créée
 */


public class ChangesDetector {

    protected static DBHandler db;

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

    /**
     * Retourne la liste des contacts de la BdD créée par l'application ORANGE
     * @param context
     * @return
     */
    public static ArrayList<Contact> getContactsFromApplicationDatabase(Context context){
        db = new DBHandler(context);

        //db.resetContacts();

        ArrayList<Contact> contactsFromApplicationDatabase = db.getAllContacts();

        return contactsFromApplicationDatabase;
    }

    /**
     * Cherche l'indice du contact avec un certain id
     * @param id
     * @param contacts
     * @return l'indice du contact avec l'id dans la liste contacts. Si ce contact n'existe pas,
     *         renvoie -1
     */
    public static int getIndexOfContactInDatabase(String id, ArrayList<Contact> contacts){
        int indexOfId = -1;
        for(int i = 0; i<contacts.size();i++){
            if(contacts.get(i).getContactId().equals(id)){
                // Si il y existe un contact dans contacts avec l'id :
                indexOfId = i;
            }
        }

        return indexOfId;
    }

    /**
     * Compare deux contacts selon leur numéro de téléphone et leur nom
     * @param contact1
     * @param contact2
     * @return true si les contacts ont même nom et n° de tel, false sinon
     */

    // TODO : un même numéro de téléphone mais écrit différemment (ex: +33 (0)... et 0...)
    // TODO : devront être traités
    public static boolean areDifferent(Contact contact1, Contact contact2){

        boolean namesBool = contact1.getFirstName().equals(contact2.getFirstName());
        boolean phoneNumber = contact1.getPhoneNumber().equals(contact2.getPhoneNumber());

        return !(namesBool && phoneNumber);
    }

    public static boolean compareAndUpdateDatabases(Context context, ArrayList<Contact> contactsFromApplicationContact, ArrayList<Contact> contactsFromApplication){
        /**
         * Retourne la liste des contacts de la BdD créée par l'application ORANGE
         * // TODO Les contacts sont comparés grâce à leur ID dans la BdD de Contacts
         * // TODO On suppose qu'il n'y a pas de doublon
         * // TODO Gérer les contacts supprimés puis remis ?
         * @param context : context de l'activité
         * @param contactsFromApplicationContact : liste des contacts issue de la BdD de l'application Contacts
         * @param : contactsFromApplication : liste des contacts issue de la BdD de l'application ORANGE
         * @return : true si il y a eu un update de la BdD, false sinon
         */

        ArrayList<Contact> contactsToUpdate = new ArrayList<Contact>(); // Contacts de l'application ORANGE qui ont été modifiés dans l'application Contacts
        ArrayList<Contact> contactsUpdated = new ArrayList<Contact>(); // Contacts de l'application Contacts qui ont été modifiés
        ArrayList<Contact> contactsCreated = new ArrayList<Contact>(); // Contacts de l'application Contacts qui ont été créés

        ArrayList<Contact> remainingContactsFromApplicationContact = new ArrayList<Contact>(contactsFromApplicationContact);
        ArrayList<Contact> remainingContactsFromApplication = new ArrayList<Contact>(contactsFromApplication);

        int indexOfContactInDatabase = 0;
        int j = 0;
        Contact contact = new Contact();


        while(j != remainingContactsFromApplicationContact.size()){

            // vaut l'indice du contact dans la base de données Orange
            // -1 si il est absent de la base de données Orange
            indexOfContactInDatabase = getIndexOfContactInDatabase(remainingContactsFromApplicationContact.get(j).getContactId(), remainingContactsFromApplication);

            if(indexOfContactInDatabase == -1){
                // Si le contact de l'application Contacts n'est pas dans la base de données ORANGE
                // le rajouter dans la base de données ORANGE
                contactsCreated.add(remainingContactsFromApplicationContact.get(j));
            }
            else {
                // Tester si il y a des modifications
                if(areDifferent(remainingContactsFromApplication.get(indexOfContactInDatabase), remainingContactsFromApplicationContact.get(j))){
                    // Si oui
                    // mettre dans le nouveau contact d'Orange les données du nouveau contact de Contacts
                    contact.setFirstName(remainingContactsFromApplicationContact.get(j).getFirstName());
                    contact.setPhoneNumber(remainingContactsFromApplicationContact.get(j).getPhoneNumber());
                    contact.setContactId(remainingContactsFromApplicationContact.get(j).getContactId());
                    contactsUpdated.add(contact);
                    // L'ancien contact de l'application ORANGE est rajouté pour entrer en param de db.modify
                    contactsToUpdate.add(remainingContactsFromApplication.get(indexOfContactInDatabase));
                }else{
                    // Si non,
                    // Si le contact n'a ni été créé ni été modifié, ne rien faire
                }

            }

            j++;
        }

        // Communication avec la base de données de l'application ORANGE
        DBHandler db = new DBHandler(context);


        // Modifier les contacts déjà existants
        // TODO
        for(int i = 0; i<contactsUpdated.size(); i++){
            //                       nouveauContact, ancienContact
            db.modifyContact(contactsUpdated.get(i), contactsToUpdate.get(i));
        }

        // Gérer les contacts supprimés
        // TODO


        // Insérer les contacts nouvellement créés

        for(int i = 0; i<contactsCreated.size(); i++){
            db.addContact(contactsCreated.get(i));
        }

        return true;

    }

    /**
     * Compare la base de données de contacts entre Orange et Contacts
     * Update la Database en conséquence
     * @param context
     * @return true si l'update a eu lieu, false sinon
     */

    public static boolean updateApplicationDatabase(Context context){

        // Les contacts de la BdD de l'application Contacts
        ArrayList<Contact> contactsFromPhoneDatabase = getContactsFromPhoneDatabase(context);

        // Les contacts de la BdD de l'application Orange
        ArrayList<Contact> contactsFromApplicationDatabase = getContactsFromApplicationDatabase(context);

        // Comparer les 2 listes de contacts et update la liste des contacts de la BdD de ORANGE

        Boolean bool = compareAndUpdateDatabases(context, contactsFromPhoneDatabase,  contactsFromApplicationDatabase);

        return bool;
    }


}
