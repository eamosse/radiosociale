/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;



/**
 *
 * @author eamosse
 */
////@RDFNamespaces({ 
//    "time ="+ Constant.TIME,
//    "rso ="+ Constant.NS,
//})
////@RDFBean("gn:Feature")
public class Duration {
    private double years; 
    private double months; 
    private double weeks; 
    private double days; 
    private double hours; 
    private double minutes; 
    private double seconds; 

    //@RDF("time:years")
    public double getYears() {
        return years;
    }

    public void setYears(double years) {
        this.years = years;
    }

    //@RDF("time:months")
    public double getMonths() {
        return months;
    }

    public void setMonths(double months) {
        this.months = months;
    }

    //@RDF("time:weeks")
    public double getWeeks() {
        return weeks;
    }

    public void setWeeks(double weeks) {
        this.weeks = weeks;
    }

    //@RDF("time:days")
    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    //@RDF("time:hours")
    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    //@RDF("time:minutes")
    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    //@RDF("time:seconds")
    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }
}
