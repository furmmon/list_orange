package com.daimajia.swipedemo;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by vincenthoulbreque on 09/09/16.
 */
public class Generator {

    protected static ArrayList<Contact> contacts = new ArrayList<Contact>();


    protected static ArrayList<Contact> contactlist1 = new ArrayList<Contact>();
    protected static ArrayList<Contact> contactlist2 = new ArrayList<Contact>();
    protected static ArrayList<Contact> contactlist3 = new ArrayList<Contact>();
    protected static ArrayList<Contact> contactlist4 = new ArrayList<Contact>();
    protected static ArrayList<Contact> contactlist5 = new ArrayList<Contact>();

    protected static DBHandler db;


    public Generator(Context context){
        db = new DBHandler(context);
        contacts = db.getAllContacts();

        }


    public ArrayList<Contact> getList1() {
        if (contacts.size() > 10) {
            return (ArrayList<Contact>) contacts.subList(1, 10);
        } else {
            return contacts;
        }
    }

}
