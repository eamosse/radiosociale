/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.test;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import fr.unice.i3s.wimmics.radio.utils.*;
import fr.unice.i3s.wimmics.radio.controller.*;
import fr.unice.i3s.wimmics.radio.model.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import thewebsemantic.Sparql;

/**
 *
 * @author eamosse
 */
public class TestFrequency extends AbstractFacade<Frequency> {

    public static void main(String[] args) {
        //new TestFrequency().runTest(1368520597999L, 1368520598009L);
        new TestFrequency().test();
    }

    public void execute() {
        String query = "PREFIX rds: <%s> "
                + "SELECT ?s WHERE {?s a <%s>. "
                + "?s rds:%s ?param. "
                + "?param rds:id ?id. "
                + "FILTER (?id=%s)}";
        query = String.format(query, Constant.NS, Constant.NS + rdfType, "category", String.valueOf(1365955752253L));
        System.out.println(query);
        Collection<Frequency> results = Sparql.exec(getModel(), Frequency.class, query);
        for (Frequency f : results) {
            System.out.println("Response " + f.getName());
        }
    }

    public TestFrequency() {
        super(Frequency.class);
    }

    public void runTest(long id1, long id2) {
        Main m = new Main();
        Category circulation = new Category();
        circulation.setDescription("Decrit les evenements en rapport avec la circulation routier");
        circulation.setId(id1);
        circulation.setName("Circulation");
        circulation.setImage("/pictures/image_bus.png");
        m.create(circulation);
        //Create another category 
        Category restauration = new Category();
        restauration.setDescription("Decrit les evenements en rapport avec la restauration");
        restauration.setId(id2 + 1);
        restauration.setName("Restauration");
        restauration.setImage("/pictures/image_food.png");
        m.create(restauration);
        System.out.println("Cat 1 " + restauration.getName());
        System.out.println("Cat 2 " + circulation.getName());
//            //Create a frequency congestion in circulmation 
        Frequency f = new Frequency();
        f.setCategory(circulation);
        f.setListen(AccessRight.PUBLIC);
        f.setPublish(AccessRight.PUBLIC);
        f.setName("Embouteillage");
        f.setId(System.currentTimeMillis());
        f.setImage("/pictures/image_bus.png");
        f.setResponseType(ResponseType.SIMPLE_CHOICE);
        Set<Response> c = new HashSet<Response>();
        c.add(new Response("Fluide", "/pictures/image_traffic_light.png"));
        c.add(new Response("Modéré", "/pictures/image_traffic_heavy.png"));
        c.add(new Response("Bloqué", "/pictures/image_traffic_standstill.png"));
        f.setTags(c);
        Set st = new HashSet();
        st.add("Sens");
        st.add("Vitesse");
        f.setMetaKeys(st);
        f.setTopic("Remarquez vous un embouteillage sur votre parcours? Alors dites le nous!");
        Administrator a = new Administrator();
        a.setEmail("test@exemple.com");
        a.setFirstName("Administrator");
        a.setLastName("Test");
        a.setId(System.currentTimeMillis());
        a.setNick("eamosse");
        a.setId(System.currentTimeMillis());
        f.setAdministrator(a);
        //System.out.println("frequencies " + circulation.getFrequencys().size());
        this.create(f);

        //            //Create a frequency congestion in circulmation 
        Frequency f1 = new Frequency();
        f1.setCategory(circulation);
        f1.setListen(AccessRight.PUBLIC);
        f1.setPublish(AccessRight.PUBLIC);
        f1.setName("Accident");
        f1.setId(System.currentTimeMillis());
        f1.setImage("/pictures/image_accident.jpg");
        f1.setResponseType(ResponseType.SIMPLE_CHOICE);
        c = new HashSet<Response>();
        c.add(new Response("Très grave", "/pictures/image_accident_major.png"));
        c.add(new Response("Pas trop grave", "/pictures/image_accident_minor.png"));
        f1.setTopic("Un accident sur la route! Alors dites le nous!");
        f1.setTags(c);
        a = new Administrator();
        a.setEmail("test@exemple.com");
        a.setFirstName("Administrator");
        a.setLastName("Test");
        a.setId(System.currentTimeMillis());
        a.setNick("eamosse");
        a.setId(System.currentTimeMillis());
        f1.setAdministrator(a);
        this.create(f1);

        Frequency f2 = new Frequency();
        f2.setCategory(circulation);
        f2.setListen(AccessRight.PUBLIC);
        f2.setPublish(AccessRight.PUBLIC);
        f2.setName("Radar");
        f2.setId(System.currentTimeMillis());
        f2.setImage("/pictures/image_radar.png");
        f2.setResponseType(ResponseType.SIMPLE_CHOICE);
        c = new HashSet<Response>();
        c.add(new Response("Mobile", "/pictures/image_radar.png"));
        c.add(new Response("Fixe", "/pictures/image_radar_fixe.png"));
        f2.setTags(c);
        f2.setTopic("Signaler un radar!");
        f2.setAdministrator(a);
        this.create(f2);

        //Create a frequency annotation in Restauration 
        Frequency an_restaurant = new Frequency();
        an_restaurant.setListen(AccessRight.PUBLIC);
        an_restaurant.setPublish(AccessRight.PUBLIC);
        an_restaurant.setName("Annotation de service");
        an_restaurant.setDescription("Annoter les services de restauration");
        an_restaurant.setImage("/pictures/image_food.png");
        an_restaurant.setCategory(restauration);
        an_restaurant.setId(System.currentTimeMillis());
        an_restaurant.setTopic("Comment trouvez ce restaurant?");
        an_restaurant.setResponseType(ResponseType.SIMPLE_CHOICE);
        c = new HashSet<Response>();
        c.add(new Response("Excellent", "/pictures/image_like.png"));
        c.add(new Response("Pas bon", "/pictures/image_dislike.png"));
        an_restaurant.setTags(c);
        an_restaurant.setAdministrator(a);
        this.create(an_restaurant);
    }

    public void request() {

        //http://xmlns.com/radiociale/ontology/0.1/Frequency/1369233376152
        Frequency f = this.find(1369233376152L);
        System.out.println("Frequency " + f.getName());

        if (f.getTags().isEmpty()) {
            System.out.println("Oopppsss");
        }else{
            Iterator<Response> iterator = f.getTags().iterator();
            while(iterator.hasNext())
                System.out.println("Response " + iterator.next().getText());
        }
    }

    private static Model query(Model model, Query query) {
        //Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        Model result = ModelFactory.createDefaultModel();
        try {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.asRDF(result, results);
        } finally {
            qexec.close();
        }
        return result;
    }

    private void test() {
        this.find(1371672844581L);
    }
}
