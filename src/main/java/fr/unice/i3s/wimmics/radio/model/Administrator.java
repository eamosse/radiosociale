/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;


import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.io.Serializable;
import java.util.List;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 *
 * @author eamosse
 */
@XmlRootElement
@Namespace(Constant.NS)
@RdfType("Administrator")
public class Administrator extends Person implements Serializable {
    private List<Frequency> frequencies;
    private Contact contact; 
    public String toString() {
        return "fr.unice.i3s.wimmics.entity.Administrator[ id=" + super.getId() + " ]";
    }
  
    @XmlTransient
    public List<Frequency> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(List<Frequency> frequencies) {
        this.frequencies = frequencies;
    }
    
    //@RDF("rso:hasContact")
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
