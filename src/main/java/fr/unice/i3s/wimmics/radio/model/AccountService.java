/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

/**
 *
 * @author eamosse
 */
public enum AccountService {
    FACEBOOK ("http://www.facebook.com"), 
    GOOGLE ("http://www.google.com"),
    TWITTER ("http://www.twitter.com");
    String account; 

    private AccountService(String a) {
        account = a; 
    }
    
    @Override
    public String toString(){
        return account; 
    }
}
