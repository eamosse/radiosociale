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
//    //@RDFNamespaces({ 
//    "gn ="+ Constant.GEONAMES,
//    "rso ="+ Constant.NS,
//})
////@RDFBean("gn:Feature")
@Namespace(Constant.NS)
@RdfType("Message")
public class Place implements Serializable {
    private String type; 
    private Feature feature; 

    //@RDF("rso:type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //@RDF("gn:Feature")
    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
