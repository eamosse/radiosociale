/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;



/**
 *
 * @author eamosse
 */
////@RDFNamespaces({ 
//    "vcard ="+ Constant.VCARD,
//    "rdf=" + Constant.RDF,
//    "phones ="+Constant.NS 
//})
////@RDFBean("foaf:Phone")
public class Telephone {
    private String number; 
    //@RDF("rdf:value")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
