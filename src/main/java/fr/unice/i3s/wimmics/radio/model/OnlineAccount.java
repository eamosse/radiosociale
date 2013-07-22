/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 *
 * @author eamosse
 */
////@RDFNamespaces({ 
//    "foaf ="+ Constant.FOAF,
//    "accounts ="+Constant.NS 
//})
////@RDFBean("foaf:onlineAccount")
@XmlRootElement
@Namespace(Constant.NS)
@RdfType("OnlineAccount")
public class OnlineAccount implements Serializable {
    private Person person;
    private Long id;
    private AccountService accountServiceHomePage; 
    private String accountName; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OnlineAccount)) {
            return false;
        }
        OnlineAccount other = (OnlineAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.unice.i3s.wimmics.entity.OnlineAccount[ id=" + id + " ]";
    }
    
    public AccountService getAccountServiceHomePage() {
        return accountServiceHomePage;
    }

    public void setAccountServiceHomePage(AccountService accountServiceHomePage) {
        this.accountServiceHomePage = accountServiceHomePage;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    /**
     * RDF properties
     */
     public void setrdfSubject(String rdfSubject){
        
    }
     //@RDFSubject(prefix = "accounts:")
     public String getrdfSubject(){
      return String.valueOf(id);
    }
     //beacause rdfgo cannot handle enum type, we render the property as String 
     //@RDF("foaf:accountServiceHomePage")
     public String getAccountService() {
        return accountServiceHomePage.toString();
    }
    
    public void setAccountService(String response){
       this.accountServiceHomePage= AccountService.valueOf(response);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * End Rdf properties
     */
    
}
