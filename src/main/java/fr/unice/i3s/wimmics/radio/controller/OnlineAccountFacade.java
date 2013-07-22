/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.controller;

import fr.unice.i3s.wimmics.radio.model.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author eamosse
 */
@Stateless
public class OnlineAccountFacade extends AbstractFacade<OnlineAccount> {

    public OnlineAccountFacade() {
        super(OnlineAccount.class);
    }
    
}
