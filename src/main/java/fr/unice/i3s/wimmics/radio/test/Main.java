/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.test;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import fr.unice.i3s.wimmics.radio.controller.*;
import fr.unice.i3s.wimmics.radio.model.*;
import thewebsemantic.RDF2Bean;

/**
 *
 * @author eamosse
 */
public class Main extends AbstractFacade<Category> {

    private com.hp.hpl.jena.rdf.model.Model m;

    public Main() {
        super(Category.class);
    }

    public static void main(String[] args) {

        new Main().runCategory(System.currentTimeMillis(), System.currentTimeMillis() + 10);

    }

    public void runCategory(long id1, long id2) {
//        VirtGraph graph = this.getGraph();
//        IDBConnection conn = new DBConnection("jdbc:virtuoso://localhost:1111", "dba", "root", "");
//        ModelMaker maker = com.hp.hpl.jena.rdf.model.ModelFactory.createModelRDBMaker(conn);
//        
        id1 = 1369227879720L;
        System.out.println("ID : " + id1);
        Category embouteillage = this.find(id1);
//        embouteillage.setDescription("Decrit les evenements en rapport avec la circulation routier");
//        embouteillage.setId(id1);
//        embouteillage.setName("Circulation 11222");
//        embouteillage.setImage("/pictures/image_bus.png");
//        this.create(embouteillage);
        System.out.println("Name " + embouteillage.getName());
        //find it 
        //http://xmlns.com/radiociale/ontology/0.1/Category/1369061205453
        Category c = this.find(id1);
        System.out.println("Name " + c.getName());
        System.out.println("Description " + c.getDescription());
        c.setName("I change your name");
        c.setDescription("I change your description also");
        this.edit(c);
        c = this.find(id1);
        System.out.println("Name " + c.getName());
        System.out.println("Description " + c.getDescription());
        
        RDF2Bean rd = new RDF2Bean(ModelFactory.createDefaultModel()); 

    }
}
