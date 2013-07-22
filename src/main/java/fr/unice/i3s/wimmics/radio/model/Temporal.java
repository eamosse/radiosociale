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
@Namespace(Constant.TIME)
@RdfType("Temporal")
public class Temporal implements Serializable {
    Temporal before; 
    Temporal after; 
}
