/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.services.utils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import fr.unice.i3s.wimmics.radio.model.Frequency;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import thewebsemantic.Bean2RDF;

/**
 *
 * @author edou
*/

@Provider
    @Produces("application/rdf+xml")
    public class RdfProvider implements MessageBodyWriter<Object> {

        @Override
        public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
            System.out.println("isWratable is called");
            return true;
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public long getSize(Object t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
            System.out.println("getSize is called");
            return -1;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void writeTo(Object t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
            System.out.println("writeTo is called");
            Model createDefaultModel = ModelFactory.createDefaultModel();
            Bean2RDF b = new Bean2RDF(createDefaultModel);
            b.save(t);
            b.getModel().write(out, "RDF/XML-ABBREV");//toString().getBytes());
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
