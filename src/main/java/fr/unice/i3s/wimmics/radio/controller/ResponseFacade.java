/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.controller;

import fr.unice.i3s.wimmics.radio.model.Response;
import javax.ejb.Stateless;

/**
 *
 * @author edou
 */
@Stateless
public class ResponseFacade extends AbstractFacade<Response> {
    public ResponseFacade(){
        super(Response.class);
    }
}
