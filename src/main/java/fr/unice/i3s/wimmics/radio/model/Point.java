/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

import fr.unice.i3s.wimmics.radio.utils.Constant;
import thewebsemantic.*;

/**
 *
 * @author eamosse
 */
////@RDFNamespaces({ 
//    "pos ="+ Constant.POSITION,
//    "rso ="+ Constant.NS,
//})
////@RDFBean("pos:Point")
@Namespace(Constant.POSITION)
@RdfType("Point")
public class Point {
    //Beacause in http://www.w3.org/2003/01/geo/wgs84_pos# lng in lat are defined as string  

    private String lng;
    private String lat;

    /**
     * @return the lng
     */
    public String getLng() {
        return lng;
    }

    /**
     * @return the lat
     */
    public String getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
    
    @Transient
    @Override
    public String toString(){
        return "[" + lat + "," + lng + "]"; 
    }
}
