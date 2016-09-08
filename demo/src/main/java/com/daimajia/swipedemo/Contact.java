package com.daimajia.swipedemo;

/**
 * Created by vincenthoulbreque on 07/09/16.
 */
public class Contact {

    protected String firstName;
    protected String phoneNumber;
    protected String id;
    protected String uri;


    public void Contact(String firstName, String phoneNumber){
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
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

    public String getId(){
        return this.id;
    }

    public void setId(String s){
        this.id = s;
    }

    /*public String getUri(){
        return this.uri;
    }

    public void setUri(String s){
        this.uri = s;
    }*/

}
