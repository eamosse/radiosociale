/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.test;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.File;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edou
 */
public class PizzaSparqlNoInf {
    /***********************************/
    /* Constants                       */
    /***********************************/

    // Directory where we've stored the local data files, such as pizza.rdf.owl
    public static final String SOURCE = "./src/main/resources/";

    // Pizza ontology namespace
    public static final String PIZZA_NS = "http://xmlns.com/radiociale/ontology/0.1#";

    /***********************************/
    /* Static variables                */
    /***********************************/

    @SuppressWarnings( value = "unused" )
    private static final org.slf4j.Logger log = LoggerFactory.getLogger( PizzaSparqlNoInf.class );

    /***********************************/
    /* Instance variables              */
    /***********************************/

    /***********************************/
    /* Constructors                    */
    /***********************************/

    /***********************************/
    /* External signature methods      */
    /***********************************/

    /**
     * @param args
     */
    public static void main( String[] args ) {
        //File f = new File("id.text");
        new PizzaSparqlNoInf().run();
    }

    public void run() {
        OntModel m = getModel();
        loadData( m );
        String prefix = "prefix rds: <" + PIZZA_NS + ">\n" +
                        "prefix rdfs: <" + RDFS.getURI() + ">\n" +
                        "prefix owl: <" + OWL.getURI() + ">\n" +
                        "prefix rdf: <" + RDF.getURI() + ">\n";


        showQuery( m,
                   prefix +
                   "select ?x ?y ?z where {"
                +  "?x a owl:ObjectProperty."
                +  "?x rdfs:comment ?y. " 
                +  "?x rdfs:label ?z; "
                + "FILTER ( lang(?y) = 'en' && lang(?z) = 'en')" 
                +   "}" );
    }

    protected OntModel getModel() {
        return ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
    }

    protected void loadData( Model m ) {
        FileManager.get().readModel( m, SOURCE + "radiosociale.owl" );
    }

    protected void showQuery( Model m, String q ) {
        Query query = QueryFactory.create( q );
        QueryExecution qexec = QueryExecutionFactory.create( query, m );
        try {
            ResultSet results = qexec.execSelect();
             while (results.hasNext()) {
                QuerySolution next = results.next();
                System.out.println(next.getResource("?x").toString().split("#")[1] + " ---> " +next.getLiteral("?y").getString());
            }
            ResultSetFormatter.out( results, m );
        }
        finally {
            qexec.close();
        }

    }

    /***********************************/
    /* Internal implementation methods */
    /***********************************/

    /***********************************/
    /* Inner class definitions         */
    /***********************************/

}
