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
//    "rso ="+ Constant.NS,
//})
//@RDFBean("rso:MetaData")
public class MetaData {
    
    
    private String key; 
    private String value; 

    //@RDF("rso:hasKey")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    //@RDF("rso:hasValue")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
