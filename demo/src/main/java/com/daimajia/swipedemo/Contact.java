package com.daimajia.swipedemo;

/**
 * Created by vincenthoulbreque on 07/09/16.
 */
public class Contact {

    protected String firstName;
    protected String lastName;
    protected String phoneNumber;


    public void Contact(String firstName, String lastName, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String s){
        this.firstName = s;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String s){
        this.lastName = s;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String s){
        this.phoneNumber = s;
    }

}
