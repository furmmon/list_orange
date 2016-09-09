package com.daimajia.swipedemo;

/**
 * Created by furmon on 09/09/2016.
 */
public class Appel {

    protected String origine;
    protected String contactid;
    protected String duree;


    public String getOrigine(){
        return this.origine;
    }

    public void setOrigine(String s){
        this.origine = s;
    }

    public String getContactId(){
        return this.contactid;
    }

    public void setContactId(String s){
        this.contactid = s;
    }

    public String getDuree(){
        return this.duree;
    }

    public void setDuree(String s){
        this.duree = s;
    }
}
