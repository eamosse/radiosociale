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
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)  
public @interface SparqlQueries {
    public SparqlAnnotation[] value();
}
