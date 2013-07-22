/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;

/**
 * REST Web Service
 *
 * @author edou
 */
public class ItemResource {

    private String name;

    /**
     * Creates a new instance of ItemResource
     */
    private ItemResource(String name) {
        this.name = name;
    }

    /**
     * Get instance of the ItemResource
     */
    public static ItemResource getInstance(String name) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ItemResource class.
        return new ItemResource(name);
    }

    /**
     * Retrieves representation of an instance of fr.unice.i3s.wimmics.radio.services.ItemResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ItemResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    /**
     * DELETE method for resource ItemResource
     */
    @DELETE
    public void delete() {
    }
}
