/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.controller;

import fr.unice.i3s.wimmics.radio.utils.Utils;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import static fr.unice.i3s.wimmics.radio.test.PizzaSparqlNoInf.PIZZA_NS;
import static fr.unice.i3s.wimmics.radio.test.PizzaSparqlNoInf.SOURCE;
import fr.unice.i3s.wimmics.radio.utils.*;
import java.net.URI;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;
import thewebsemantic.RdfType;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

/**
 *
 * @author eamosse
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    private com.hp.hpl.jena.rdf.model.Model model;
    protected String rdfType;
    //protected String package; 
    String findAll;
    String findById;
    String findByUri;
    String countSelect;
    Model myModel;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        Annotation[] annotations = entityClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof RdfType) {
                RdfType myAnnotation = (RdfType) annotation;
                rdfType = myAnnotation.value();
            } else if (annotation instanceof SparqlQueries) {
                SparqlQueries ann = (SparqlQueries) annotation;
                SparqlAnnotation[] values = ann.value();
                for (SparqlAnnotation myAnnotation : values) {
                    if (myAnnotation.name().equalsIgnoreCase(Constant.findAll)) {
                        findAll = Utils.prefixes() + myAnnotation.value();
                    } else if (myAnnotation.name().equalsIgnoreCase(Constant.findById)) {
                        findById = Utils.prefixes() + myAnnotation.value();
                    } else if (myAnnotation.name().equalsIgnoreCase(Constant.findByUri)) {
                        findByUri = Utils.prefixes() + myAnnotation.value();
                    } else if (myAnnotation.name().equalsIgnoreCase(Constant.count)) {
                        countSelect = Utils.prefixes() + myAnnotation.value();
                    }
                }
            }
        }

        if (rdfType == null) {
            rdfType = entityClass.getSimpleName();
        }

    }

    public void create(T entity) {
        model = getModel();
        Bean2RDF writer = new Bean2RDF(model);
        //convert the entity to statement ==> rdf triple in other words 
        writer.save(entity);
        StmtIterator iter = model.listStatements();
        VirtGraph graph = getGraph();
        try {
            //for each statement add it to the graph 
            while (iter.hasNext()) {
                //get the next statement 
                Statement stmt = iter.next();
                //convert the statement as rdf triple 
                Triple triple = stmt.asTriple();
                //add it to the graph 
                graph.add(triple);
            }
        } finally {
            if (iter != null) {
                iter.close();
            }
        }
    }

    //TODO: 
    public void edit(T entity) {

        model = getModel();
        Bean2RDF writer = new Bean2RDF(model);
        writer.save(entity);
        model.write(System.out);
        StmtIterator iter = model.listStatements();
        VirtGraph graph = getGraph();
        try {
            //for each statement add it to the graph 
            while (iter.hasNext()) {
                //get the next statement 
                Statement stmt = iter.next();
                //convert the statement as rdf triple 
                Triple triple = stmt.asTriple();
                if (triple.getObject().isLiteral()) {
                    System.out.println(triple.getSubject() + " has " + triple.getPredicate() + " and " + triple.getObject() + " is a Literal");
                }
                if (!graph.contains(triple)) {
                    if (triple.getObject().isLiteral()) {
                        String q = "delete from <" + graph.getGraphName() + "> { <" + triple.getSubject() + "> <" + triple.getPredicate() + "> ?p }" + " where { <" + triple.getSubject() + "> <" + triple.getPredicate() + "> ?p }";
                        System.out.println("SPARQL " + q);
                        VirtuosoUpdateRequest create = VirtuosoUpdateFactory.create(q, graph);
                        create.exec();
                    }
                    //System.out.println("SPARQL " + q);
                    graph.add(triple);
                } else {
                    System.out.println(triple.getPredicate().getURI() + " Already exist");
                }
                //add it to the graph 
                //graph.add(triple);
            }
        } finally {
            if (iter != null) {
                iter.close();
            }
        }
        //create(entity);
    }

    public void remove(T entity) throws IOException {
        model = getModel();
        model.write(System.out, "RDF/XML");
        Bean2RDF writer = new Bean2RDF(model);
        writer.save(entity);
        StmtIterator iter = model.listStatements();
        VirtGraph graph = getGraph();
        //graph.add(model.listStatements());
        try {
            //for each statement add it to the graph 
            while (iter.hasNext()) {
                //get the next statement 
                Statement stmt = iter.next();
                //convert the statement as rdf triple 
                Triple triple = stmt.asTriple();
                //add it to the graph 
                graph.delete(triple);

            }
        } finally {
            if (iter != null) {
                iter.close();
            }
        }
    }

    public T find(Object id) {
        String queryString = "";
        if (id instanceof Long) {
            queryString = String.format(findById, id);
        } else if (id instanceof URI || id instanceof String) {
            queryString = String.format(findByUri, id);
        }
        System.out.println(queryString);
        if (queryString != null) {
            Query query = QueryFactory.create(queryString);
            List<T> results = executeDescribe(query);
            if (results != null && !results.isEmpty()) {
                return results.get(0);
            }
        }
        return null;
    }

    public List<T> executeDescribe(Query query) {
        //System.out.println(query.toString());
        VirtGraph graph = getGraph();
        QueryExecution vqe;
        vqe = VirtuosoQueryExecutionFactory.create(query, graph);
        Model m = vqe.execDescribe();
        //m.add(Utils.completModel(entityClass));
        m.setNsPrefixes(Utils.prefix);
        m.write(System.out, "RDF/XML");
        RDF2Bean reader = new RDF2Bean(m);
        System.out.println("Entity " + entityClass);
        Collection<T> list = reader.load(entityClass);
        if (list instanceof List) {
            return (List<T>) list;
        } else {
            return new ArrayList<T>(list);
        }
        //return list;
    }

    public Query createQuery(String queryString) {
        Query query = QueryFactory.create(Utils.prefixes() + queryString);
        return query;
    }

    public ResultSet executeSelect(Query query) {
        VirtGraph graph = getGraph();
        QueryExecution vqe;
        vqe = VirtuosoQueryExecutionFactory.create(query, graph);
        ResultSet res = vqe.execSelect();
        return res;
    }

    private void convertToModel(ResultSet rs) {
        ResultSetFormatter.outputAsXML(System.out, rs);
        model = ModelFactory.createDefaultModel();
        ResultSetFormatter.asRDF(model, rs);
        model.setNsPrefixes(Utils.prefix);
        model.write(System.out, "RDF/XML");
        //model = rs.getResourceModel();
        model.setNsPrefixes(Utils.prefix);
        while (rs.hasNext()) {
            QuerySolution solution = rs.next();
            System.out.println(solution.toString());
        }
//        model.write(System.out);
    }

    public List<T> findAll() {
        System.out.println(findAll);
        Query query = QueryFactory.create(findAll);
        query.addOrderBy("id", Query.ORDER_ASCENDING);
        return executeDescribe(query);
    }

    public List<T> findRange(int[] range) {
        if (range == null) {
            range = new int[]{0, 10};
        } else {
            if (range.length < 2) {
                range[0] = 0;
                range[1] = 10;
            }
        }
        String queryString = findAll;
        Query query = QueryFactory.create(queryString);
        query.addOrderBy("id", Query.ORDER_ASCENDING);
        query.setLimit(range[1]);
        query.setOffset(range[0]);
        System.out.println(query.toString());
        return findAll();
    }

    public int count() {
        Query query = QueryFactory.create(countSelect, Syntax.syntaxARQ);
        ResultSet res = executeSelect(query);
        if (res.hasNext()) {
            return res.nextSolution().getLiteral("count").getInt();
        }
        return 0;
    }

    public void setEntityClass(Class<T> clazz) {
        entityClass = clazz;
    }

    public Model getModel() {
        model = ModelFactory.createDefaultModel();
        model.setNsPrefixes(Utils.prefix);
        return model;
    }

    public VirtGraph getGraph() {
        //Return the graph if it already exists then create a fresh one
        VirtGraph graph = new VirtGraph("radiosociale", "jdbc:virtuoso://localhost:1111", "dba", "root");
        //get all the statement for the created model
        return graph;
    }

    public Map<String, String> loadProperties(String url) {

        OntModel m = getOntModel();
        loadData(url, m);
        Map<String, String> maps = new HashMap<String, String>();
        String prefix = "prefix rds: <" + PIZZA_NS + ">\n"
                + "prefix rdfs: <" + RDFS.getURI() + ">\n"
                + "prefix owl: <" + OWL.getURI() + ">\n"
                + "prefix rdf: <" + RDF.getURI() + ">\n";
        String q =
                prefix
                + "select ?property ?comment ?label where {"
                + "?property a owl:ObjectProperty."
                + "?property rdfs:comment ?comment. "
                + "?property rdfs:label ?label; "
                + "FILTER ( lang(?comment) = 'en' && lang(?label) = 'en')"
                + "}";
        Query query = QueryFactory.create(q);
        QueryExecution qexec = QueryExecutionFactory.create(query, m);

        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution next = results.next();
                maps.put(next.getResource("property").toString().split("#")[1], next.getLiteral("comment").getString());
            }
            ResultSetFormatter.out(results, m);
        } finally {
            qexec.close();
        }

        return maps;
    }

    protected OntModel getOntModel() {
        return ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    }

    protected void loadData(String SOURCE, Model m) {
        //String SOURCE = "./src/main/resources/";
        FileManager.get().readModel(m, SOURCE + "/radiosociale.owl");
    }
}
