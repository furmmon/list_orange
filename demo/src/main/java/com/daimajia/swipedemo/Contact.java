package com.daimajia.swipedemo;

/**
 * Created by vincenthoulbreque on 07/09/16.
 */
public class Contact {

    protected String firstName;
    protected String phoneNumber;
    protected String contactId;
    protected String dbId;


    public void Contact(String firstName, String phoneNumber){
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    public void Contact(Contact contact){
        this.firstName = contact.getFirstName();
        this.phoneNumber = contact.getPhoneNumber();
        this.contactId = contact.getContactId();
        this.dbId = null;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String s){
        this.firstName = s;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String s){
        this.phoneNumber = s;
    }

    public String getContactId(){
        return this.contactId;
    }

    public void setContactId(String s){
        this.contactId = s;
    }

    public String getDbId(){
        return this.dbId;
    }

    public void setDbId(String s){
        this.dbId = s;
    }

}
