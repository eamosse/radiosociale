/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;



/**
 *
 * @author eamosse
 */
//@RDFNamespaces({ 
//    "time ="+ Constant.TIME
//})
//@RDFBean("time:interval")
public class Interval extends Temporal {
    private Instant begin; 
    private Instant end; 

    //@RDF("time:hasBeginning")
    public Instant getBegin() {
        return begin;
    }

    public void setBegin(Instant begin) {
        this.begin = begin;
    }

    //@RDF("time:hasEnd")
    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }
}
