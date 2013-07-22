/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

import fr.unice.i3s.wimmics.radio.utils.Constant;
import javax.xml.bind.annotation.XmlRootElement;
import thewebsemantic.Id;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 *
 * @author eamosse
 */
@XmlRootElement
@Namespace(Constant.NS)
@RdfType("MessageFeed")
public class MessageFeed {
    private Response content; 
    private Frequency frequency;
    private Feature feature;
    private Duration validity; 
    private Interval interval; 
    private Long id; 
    private int occurence; 

    //@RDF("rso:content")
    public Response getContent() {
        return content;
    }

    public void setContent(Response content) {
        this.content = content;
    }

    //@RDF("rso:Frequency")
    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    //@RDF("gn:Feature")
    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    //@RDF("time:Duration")
    public Duration getValidity() {
        return validity;
    }

    public void setValidity(Duration validity) {
        this.validity = validity;
    }

     //@RDF("time:Interval")
    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the occurence
     */
    public int getOccurence() {
        return occurence;
    }

    /**
     * @param occurence the occurence to set
     */
    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }
    
}
