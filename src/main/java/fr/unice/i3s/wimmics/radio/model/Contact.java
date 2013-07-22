/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;


import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.io.Serializable;
import thewebsemantic.*;

/**
 *
 * @author eamosse
 */
////@RDFNamespaces({ 
//    "vcard ="+ Constant.VCARD,
//    "rso ="+ Constant.NS,
//    "contacts="+Constant.NS + "contact/"
//})
////@RDFBean("rso:Contact")
@Namespace(Constant.VCARD)
@RdfType("Adress")
public class Contact implements Serializable{
   private Adresse adresse; 
   private Telephone telephone; 

   //@RDF("vcard:adr") 
   @RdfProperty(Constant.VCARD + "adr")
   public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    //@RDF("vcard:tel") 
    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }
   
}
