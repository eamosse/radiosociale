/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;



/**
 *
 * @author eamosse
 */
//@RDFNamespaces({ 
//    "vcard ="+ Constant.VCARD,
//})
 //@RDFBean("vcard:geo")       
 public class Geo{
        private double longitue; 
        private double latitude; 

        //@RDF("vcard:longitue")
        public double getLongitue() {
            return longitue;
        }

        public void setLongitue(double longitue) {
            this.longitue = longitue;
        }

        //@RDF("vcard:latitude")
        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }