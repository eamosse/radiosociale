/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

/**
 *
 * @author eamosse
 */
public enum PlaceType {
    ROUTE("Route");
    
    String place; 
    
    PlaceType(String place){
        this.place = place; 
    }
    
    @Override
    public String toString(){
        return place; 
    }
}
