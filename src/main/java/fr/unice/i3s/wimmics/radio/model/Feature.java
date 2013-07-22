/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

import fr.unice.i3s.wimmics.radio.utils.SparqlQueries;
import fr.unice.i3s.wimmics.radio.utils.SparqlAnnotation;
import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.io.Serializable;
import thewebsemantic.Id;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Transient;

/**
 *
 * @author eamosse
 */
////@RDFNamespaces({ 
//    "gn ="+ Constant.GEONAMES,
//    "pos ="+ Constant.POSITION,
//})
////@RDFBean("gn:Feature")
@Namespace(Constant.GEONAMES)
@RdfType("Feature")
@SparqlQueries({
@SparqlAnnotation(name = Constant.findByUri, value = "describe ?x where {{?x rdf:type gn:Feature. "
        + "filter (str(?x)= \"%s\")} "
        + "UNION { ?x rdf:type rdfs:Class}"
        + "UNION { ?x rdf:type wgs:Point}"
        + "}")
})
public class Feature implements Serializable {

    private String long_name;
    private String short_name;
    String[] types;
    private Point point;
    private Feature nearby;

    /**
     * @return the name
     */
    public String getName() {
        return long_name;
    }

    /**
     * @return the nearby
     */
    public Feature getNearby() {
        return nearby;
    }

    public void setName(String name) {
        this.long_name = name;
    }

    public void setNearby(Feature nearby) {
        this.nearby = nearby;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    /**
     * @return the long_name
     */
    @Id
    public String getLong_name() {
        return long_name;
    }

    /**
     * @param long_name the long_name to set
     */
    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    /**
     * @return the types
     */
    public String[] getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(String[] types) {
        this.types = types;
    }
    
    @Transient
    @Override
    public String toString(){
        return point.toString(); 
    }
}
