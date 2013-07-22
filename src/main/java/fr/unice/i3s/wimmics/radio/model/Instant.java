/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;


import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.util.Date;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 *
 * @author eamosse
 */

////@RDFNamespaces({ 
//    "time ="+ Constant.TIME
//})
////@RDFBean("time:Instant")
@Namespace(Constant.TIME)
@RdfType("Instant")
public class Instant extends Temporal {
    
    private Date date; 

    /**
     * @return the date
     */
    //@RDF("time:inXSDDateTime")
    @RdfProperty(Constant.TIME+ "inXSDDateTime")
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
