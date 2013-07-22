/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

import fr.unice.i3s.wimmics.radio.utils.SparqlQueries;
import fr.unice.i3s.wimmics.radio.utils.SparqlAnnotation;
import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Transient;

/**
 *
 * @author eamosse
 */
@XmlRootElement
@Namespace(Constant.NS)
@RdfType("Frequency")
@SparqlQueries({
    @SparqlAnnotation(name = Constant.findAll,
            value = "DESCRIBE * \n"
            + " WHERE{\n"
            + "{?x rdf:type rds:Frequency.\n"
            + " ?x rds:responseType ?responseType.\n"
            + " ?x rds:publish ?publish.\n"
            + " ?x rds:listen ?listen.\n"
            + " ?x rds:id ?id.\n"
            + "}"
            + "UNION {?x rdf:type rdfs:Class}"
            + "UNION {?x rdf:type rds:Category}"
            + "UNION {?x rdf:type rds:Response}"
            + "UNION {?x rdf:type rds:Administrator}"
            + "UNION {?x rdf:type rds:ResponseType}"
            + "UNION {?x rdf:type rds:AccessRight}"
            + "}"),
    @SparqlAnnotation(name = Constant.count,
            value = "select (count(*) AS ?count) \n"
            + "where\n"
            + "{\n"
            + "?instance rdf:type rds:Frequency\n"
            + "}"),
    @SparqlAnnotation(name = Constant.findById,
            value = "DESCRIBE * \n"
            + " WHERE{\n"
            + "{?x rdf:type rds:Frequency.\n"
            + " ?x rds:responseType ?responseType.\n"
            + " ?x rds:publish ?publish.\n"
            + " ?x rds:listen ?listen.\n"
            + " ?x rds:id ?id.\n"
            + "filter (?id =xsd:long(%s))"
            + "}"
            + "UNION {?x rdf:type rdfs:Class}"
            + "UNION {?x rdf:type rds:Category}"
            + "UNION {?x rdf:type rds:Response}"
            + "UNION {?x rdf:type rds:Administrator}"
            + "UNION {?x rdf:type rds:ResponseType}"
            + "UNION {?x rdf:type rds:AccessRight}"
            + "}"),
     @SparqlAnnotation(name = Constant.findByUri,
            value = "DESCRIBE * \n"
            + " WHERE{\n"
            + "{?x rdf:type rds:Frequency.\n"
            + " ?x rds:responseType ?responseType.\n"
            + " ?x rds:publish ?publish.\n"
            + " ?x rds:listen ?listen.\n"
            + "filter (?x =<%s>)"
            + "}"
            + "UNION {?x rdf:type rdfs:Class}"
            + "UNION {?x rdf:type rds:Category}"
            + "UNION {?x rdf:type rds:Response}"
            + "UNION {?x rdf:type rds:Administrator}"
            + "UNION {?x rdf:type rds:ResponseType}"
            + "UNION {?x rdf:type rds:AccessRight}"
            + "}")
   })
public class Frequency implements Serializable {

    private Long id;
    private Category category;
    private String name;
    private String description;
    private String topic;
    private ResponseType responseType;
    private AccessRight publish;
    private AccessRight listen;
    private Collection<Response> tags;
    private Administrator administrator;
    private Collection<String> metaKeys;
    private String image;

    @thewebsemantic.Id
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
        if (!(object instanceof Frequency)) {
            return false;
        }
        Frequency other = (Frequency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    //@RDF("rso:hasParent")

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //@RDF("rso:hasName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //@RDF("rso:hasDescription")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //@RDF("rso:hasTopic")

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public AccessRight getPublish() {
        return publish;
    }

    public void setPublish(AccessRight publish) {
        System.out.println("Value for publish " + publish.name() + " " + publish.getClass().getName());
        this.publish = publish;
    }

    public AccessRight getListen() {
        return listen;
    }

    public void setListen(AccessRight listen) {
        System.out.println("Value for listen " + listen.name() + " " + listen.getClass().getName());
        this.listen = listen;
    }
    //@RDF("rso:hasTags")
    //@RDFContainer(ContainerType.ALT)

   

    /**
     * @return the responseType
     */
    public ResponseType getResponseType() {
        return responseType;
    }

    /**
     * @param responseType the responseType to set
     */
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    //@RDF("rso:hasAdministrator")
    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

   

    //@RDF("rso:hasImage")
    public String getImage() {
        return image;
    }

    
    public void setImage(String image) {
        this.image = image;
    }
    
    @Transient
    public Response getTag(String uri){
       if(tags==null || tags.isEmpty())
           return null; 
       for(Response tag : tags)
           if(tag.getUri().equals(uri))
               return tag; 
       return null;
    }

    public Collection<Response> getTags() {
        return tags;
    }

    public void setTags(Collection<Response> tags) {
        this.tags = tags;
    }

    public Collection<String> getMetaKeys() {
        return metaKeys;
    }

    public void setMetaKeys(Collection<String> metaKeys) {
        this.metaKeys = metaKeys;
    }
}
