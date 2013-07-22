/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

import fr.unice.i3s.wimmics.radio.utils.SparqlQueries;
import fr.unice.i3s.wimmics.radio.utils.SparqlAnnotation;
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
@XmlRootElement
@Namespace(Constant.NS)
@RdfType("Category")
@SparqlQueries({
    @SparqlAnnotation(name = Constant.findAll,
            value = "DESCRIBE * \n"
            + " WHERE{\n"
            + "{?x rdf:type rds:Category.\n"
            + "}\n"
            + "UNION {?x rdf:type rdfs:Class}\n"
            + "}"),
    @SparqlAnnotation(name = Constant.count,
            value = "select (count(*) AS ?count) \n"
            + "where\n"
            + "{\n"
            + "?instance rdf:type rds:Category\n"
            + "}"),
    @SparqlAnnotation(name = Constant.findById,
            value = "DESCRIBE * \n"
            + "WHERE{\n"
            + "{?x rdf:type rds:Category.\n"
            + "?x rds:id ?id.\n"
            + "FILTER(?id=xsd:long(%s))\n"
            + "}"
            + "UNION {?x rdf:type rdfs:Class}"
            + "}"),
     @SparqlAnnotation(name = Constant.findByUri,
            value = "DESCRIBE * \n"
            + "WHERE{\n"
            + "{?x rdf:type rds:Category.\n"
            + "FILTER(?x=<%s>)\n"
            + "}"
            + "UNION {?x rdf:type rdfs:Class}"
            + "}")
})
public class Category implements Serializable {

    private List<Frequency> frequencys;
    private Long id;
    private String name;
    private String description;
    private Category parent;
    private String image;

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
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    /**
     * @return the name
     */
    //@RDF("rso:hasName")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Frequency> getFrequencys() {
        return frequencys;
    }

    public void setFrequencys(List<Frequency> frequencys) {
        this.frequencys = frequencys;
    }

    //@RDF("rso:hasImage")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //@RDF("rso:hasDescription")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //@RDF("rso:hasParent")

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @thewebsemantic.Id
    public String id() {
        return String.valueOf(id);
    }
}
