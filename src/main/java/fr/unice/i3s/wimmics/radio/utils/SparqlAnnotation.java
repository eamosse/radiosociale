/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author eamosse
 */
@Target({ElementType.TYPE}) //Type of the element on which this annotation can be add (TYPE mean on a class, FIELD mean on attribute etc...) 
@Retention(RetentionPolicy.RUNTIME)  //Runtime means that this annotation should be evaluate on runtime 
public @interface SparqlAnnotation {
    String name(); //the name of the annotation
    String value(); //the value of the annotation 
    
    //Example of use 
//    @SparqlAnnotation(name = "say hello",
//            value = "hrllo world")
    
}
