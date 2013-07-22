/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.io.Serializable;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 *
 * @author eamosse
 */
////@RDFNamespaces({ 
//    "vcard ="+ Constant.VCARD,
//    "adresses="+Constant.NS + "adresses/"
//})
//@RDFBean("vcard:Adress")
@Namespace(Constant.VCARD)
@RdfType("Adress")
public abstract class Adresse implements Serializable{
    private String street; 
    private String locality; 
    private String postalCode;
    private String country; 
    private Geo geo; 

    //@RDF("vcard:street-address")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    //@RDF("vcard:locality")
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
    //@RDF("vcard:postal-code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    //@RDF("vcard:country-name")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    //@RDF("vcard:geo")
    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

}
