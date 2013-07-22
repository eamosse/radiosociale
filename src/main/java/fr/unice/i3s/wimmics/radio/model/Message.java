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
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 *
 * @author eamosse
 */
@XmlRootElement
@Namespace(Constant.NS)
@RdfType("Message")
@SparqlQueries({
    @SparqlAnnotation(name = Constant.findAll,
            value = "DESCRIBE * \n"
            + " WHERE{\n"
            + "{?x rdf:type rds:Message.\n"
            + " ?x rds:content ?content.\n"
            + " ?x gn:feature ?feature.\n"
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
    @SparqlAnnotation(name = Constant.findByUri,
            value = "DESCRIBE ?x ?t \n"
            + " WHERE {\n"
            + "{?x rdf:type rds:Message.\n"
            + " ?x rds:feature ?feature.\n"
            + " ?x rds:publishDate ?publishDate.\n"
            + " ?x rds:content ?content.\n"
            + " ?x rds:frequency ?frequency.\n"
            + "OPTIONAL {?x rds:metadatas ?metas. ?x rds:person ?person}"
            + "filter (?x =<%s>)"
            + "}"
            + "UNION {?t rdf:type rdfs:Class}"
            + "UNION {?t rdf:type time:Instant filter (?t = ?publishDate)}"
            + "UNION {?t rdf:type gn:Feature filter (?t = ?feature)}"
            + "UNION {?t rdf:type rds:Response filter (?t=?content)}"
            + "UNION {?t rdf:type rds:Frequency filter (?t =?frequency)}"
            + "}"),
    @SparqlAnnotation(name = Constant.findById,
            value = "DESCRIBE ?x \n"
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
            + "}")
})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Response content;
    private Frequency frequency;
    private Feature feature;
    private Person person;
    private List<MetaData> metadatas;
    private Instant publishDate;

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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.unice.i3s.wimmics.entity.Message[ id=" + id + " ]";
    }

    //@RDF("rso:hasContent")
    public Response getContent() {
        return content;
    }

    public void setContent(Response content) {
        this.content = content;
    }

    //@RDF("rso:hasFrequency")
    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    //@RDF("rso:hasPostion")
    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    //@RDF("rso:hasPublisher")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    //@RDF("rso:hasMetadata")
    //@RDFContainer(RDFContainer.ContainerType.SEQ)
    public List<MetaData> getMetadatas() {
        return metadatas;
    }

    public void setMetadatas(List<MetaData> metadatas) {
        this.metadatas = metadatas;
    }

    //@RDF("time:Instant")
    public Instant getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }

    /**
     *
     */
    //@RDFSubject(prefix = "messages:")
    public String getrdfSubject() {
        return String.valueOf(id);
    }
    /**
     *
     */
}
