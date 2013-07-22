/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

/**
 *
 * @author eamosse
 */
public enum ResponseType {
    /**
     *
     */
    SIMPLE_CHOICE("Simple Choice"),
    MULTIPLE_CHOICE("Multiple Choice"),
    OPENED("Opened Response");
    private String choice; 
    
    ResponseType(String choice){
       this.choice = choice;  
    }
    
    @Override
    public String toString(){
        return choice;
    }
    
}
