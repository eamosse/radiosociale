/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.views;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import fr.unice.i3s.wimmics.radio.controller.MessageFacade;
import fr.unice.i3s.wimmics.radio.model.Feature;
import fr.unice.i3s.wimmics.radio.model.Frequency;
import fr.unice.i3s.wimmics.radio.model.Message;
import fr.unice.i3s.wimmics.radio.model.MessageFeed;
import fr.unice.i3s.wimmics.radio.model.Point;
import fr.unice.i3s.wimmics.radio.model.Response;
import fr.unice.i3s.wimmics.radio.utils.Constant;
import fr.unice.i3s.wimmics.radio.utils.Utils;
import fr.unice.i3s.wimmics.rss.SimpleRssGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import thewebsemantic.RDF2Bean;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 *
 * @author edou
 */
@ManagedBean(name = "mapsController")
@SessionScoped
public class MapsController {

    @EJB
    private MessageFacade messageFacade;
    private MapModel simpleModel;

    public MapsController() {
//        //Shared coordinates  
//        LatLng coord1 = new LatLng(36.879466, 30.667648);  
//        LatLng coord2 = new LatLng(36.883707, 30.689216);  
//        LatLng coord3 = new LatLng(36.879703, 30.706707);  
//        LatLng coord4 = new LatLng(36.885233, 30.702323);  
//          
//        //Basic marker  
//        simpleModel.addOverlay(new Marker(coord1, "Konyaalti"));  
//        simpleModel.addOverlay(new Marker(coord2, "Ataturk Parki"));  
//        simpleModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));  
//        simpleModel.addOverlay(new Marker(coord4, "Kaleici"));  
    }

    public MapModel getSimpleModel() {
        simpleModel = new DefaultMapModel();
        ArrayList<Message> messages = runIt();
//        List<Message> messages = messageFacade.findAll();
        System.out.println("Mesaaaaaaaaaaaaaggggggggggggggggggggggggggggg " + messages.size());
        for (Message message : messages) {
            double lat = Double.parseDouble(message.getFeature().getPoint().getLat());
            double lng = Double.parseDouble(message.getFeature().getPoint().getLng());
            LatLng coord1 = new LatLng(lat, lng);
            simpleModel.addOverlay(new Marker(coord1, message.getContent().getText()));
            System.out.println("Message " + message.getContent().getText());
        }
        return simpleModel;
    }

    public ArrayList<Message> runIt() {
        String query1 = Utils.prefixes() + "\n"
                + "PREFIX  wgs:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
                + "PREFIX  rds:  <http://xmlns.com/radiociale/ontology/0.1/> \n"
                + "PREFIX gn: <http://www.geonames.org/ontology#>\n"
                + "SELECT * WHERE\n"
                + "{\n"
                + "{\n"
                + " ?x rdf:type rds:Message; rds:id ?id_message; rds:content ?content; rds:feature ?feature.\n"
                + "    ?content rds:id ?id_content; rds:text ?text.     \n"
                + "    ?feature gn:short_name ?short_name; gn:point ?point. \n"
                + "    ?point wgs:lng ?lng; wgs:lat?lat.\n"
                + "}\n"
                + "}";



        VirtGraph graph = messageFacade.getGraph();
        QueryExecution vqe;
        vqe = VirtuosoQueryExecutionFactory.create(query1, graph);
        ResultSet results = vqe.execSelect();
        ArrayList<Message> messages = new ArrayList<Message>(); 
        while(results.hasNext()){
            QuerySolution next = results.next(); 
            long idMessage = next.getLiteral("id_message").getLong();
            long idContent = next.getLiteral("id_content").getLong();
             String text = next.getLiteral("text").getString();
            String featureName = next.getLiteral("short_name").getString();
            String longitude = next.getLiteral("lng").getString();
            String latitude = next.getLiteral("lat").getString();
            
            Message m = new Message(); 
            m.setId(idMessage);
            Response r  = new Response(); 
            r.setText(text);
            r.setId(idContent);
            Feature f = new Feature(); 
            f.setShort_name(featureName);
            Point p = new Point(); 
            p.setLat(latitude);
            p.setLng(longitude);
            f.setPoint(p);
            m.setContent(r);
            m.setFeature(f);
            messages.add(m);
        
        }
    return messages;
    }
}
