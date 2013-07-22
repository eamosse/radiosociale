/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.services;

import fr.unice.i3s.wimmics.radio.controller.AbstractFacade;
import fr.unice.i3s.wimmics.radio.model.Person;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author eamosse
 */
@Stateless
@Path("/person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Person entity) {
        try {
            super.create(entity);
        }  catch (Exception  ex) {
            Logger.getLogger(PersonFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Person entity) {
        try {
            super.edit(entity);
        } catch (Exception  ex) {
            Logger.getLogger(PersonFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            super.remove(super.find(id));
        } catch (Exception  ex) {
            Logger.getLogger(PersonFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Person find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (Exception  ex) {
            Logger.getLogger(PersonFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Person> findAll() {
        try {
            return super.findAll();
        } catch (Exception  ex) {
            Logger.getLogger(PersonFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (Exception  ex) {
            Logger.getLogger(PersonFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        try {
            return String.valueOf(super.count());
        } catch (Exception  ex) {
            Logger.getLogger(PersonFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
