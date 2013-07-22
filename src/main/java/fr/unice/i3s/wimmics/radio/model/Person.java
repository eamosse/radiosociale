/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;


import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 *
 * @author eamosse
 */
////@RDFNamespaces({ 
//    "foaf ="+ Constant.FOAF,
//    "persons ="+Constant.NS + "person/" 
//})
////@RDFBean("foaf:Person")
@XmlRootElement
@Namespace(Constant.NS)
@RdfType("Person")
public class Person implements Serializable {
    private List<Message> messages;
    private Long getId;
    private String firstName; 
    private String lastName;
    private String gender; 
    private String email; 
    private String nick; 
    private String img; 
    private List<OnlineAccount> accounts; 
    
    @thewebsemantic.Id
    public Long getId() {
        return getId;
    }

    public void setId(Long id) {
        this.getId = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId != null ? getId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the getId fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.getId == null && other.getId != null) || (this.getId != null && !this.getId.equals(other.getId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.unice.i3s.wimmics.entity.Person[ id=" + getId + " ]";
    }
    
    //@RDF("foaf:firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    //@RDF("foaf:lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    //@RDF("foaf:gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    //@RDF("foaf:mbox")
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    //@RDF("foaf:nick")
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    //@RDF("foaf:accounts")
    //@RDFContainer(ContainerType.ALT)
    @XmlTransient
    public List<OnlineAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<OnlineAccount> accounts) {
        this.accounts = accounts;
    }
    
     //@RDF("foaf:img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    /**
     * RDF properties 
     */
     //@RDFSubject(prefix = "persons:")
     public String getrdfSubject(){
      return String.valueOf(getId);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    
}
