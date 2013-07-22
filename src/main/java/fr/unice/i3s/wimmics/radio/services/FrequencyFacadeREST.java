/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.services;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import fr.unice.i3s.wimmics.radio.controller.AbstractFacade;
import fr.unice.i3s.wimmics.radio.controller.FrequencyFacade;
import fr.unice.i3s.wimmics.radio.model.Frequency;
import fr.unice.i3s.wimmics.radio.utils.Constant;
import fr.unice.i3s.wimmics.radio.utils.QueryHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import thewebsemantic.Bean2RDF;

/**
 *
 * @author eamosse
 */
@Stateless
@Path("/frequency")
public class FrequencyFacadeREST extends AbstractFacade<Frequency> {
    @EJB
    private FrequencyFacade frequencyFacade;

    public FrequencyFacadeREST() {
        super(Frequency.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Frequency entity) {
        try {
            super.create(entity);
        } catch (Exception ex) {
            Logger.getLogger(FrequencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Frequency entity) {
        try {
            super.edit(entity);
        } catch (Exception ex) {
            Logger.getLogger(FrequencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            super.remove(super.find(id));
        } catch (Exception ex) {
            Logger.getLogger(FrequencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json","application/rdf+xml"})
    public Frequency find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (Exception ex) {
            Logger.getLogger(FrequencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @GET
   // @Path("{id}")
    @Produces({"application/rdf+xml"})
    @Path("rdf/{id}")
    public Frequency findMe(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (Exception ex) {
            Logger.getLogger(FrequencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    

    @GET
    @Override
    @Produces({"application/xml", "application/json","application/rdf+xml"})
    public List<Frequency> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            Logger.getLogger(FrequencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json","application/rdf+xml"})
    public List<Frequency> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (Exception ex) {
            Logger.getLogger(FrequencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        try {
            return String.valueOf(super.count());
        } catch (Exception ex) {
            Logger.getLogger(FrequencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("bycategory/{id}")
    @Produces({"application/xml", "application/json","application/rdf+xml"})
    public Collection<Frequency> getbyCategory(@PathParam("id") Long idCategory) {
        Collection<Frequency> frequencies = frequencyFacade.findByCategory(idCategory);
        return frequencies;
    }

    private Collection<Frequency> findByCategory(Long id) {
        String query = String.format(QueryHelper.findByCategory, id);
        query = String.format(query, Constant.NS, Constant.NS + rdfType, "category", String.valueOf(id));
        Collection<Frequency> results = executeDescribe(createQuery(query));
        return results;
    }

    
}