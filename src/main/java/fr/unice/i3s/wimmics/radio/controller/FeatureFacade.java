/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.controller;

import fr.unice.i3s.wimmics.radio.model.*;
import javax.ejb.Stateless;

/**
 *
 * @author eamosse
 */
@Stateless
public class FeatureFacade extends AbstractFacade<Feature> {
    public FeatureFacade(){
        super(Feature.class);
    }
}
