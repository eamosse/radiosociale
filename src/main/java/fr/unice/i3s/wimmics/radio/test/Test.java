/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.test;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import fr.unice.i3s.wimmics.radio.controller.*;
import fr.unice.i3s.wimmics.radio.model.*;
import fr.unice.i3s.wimmics.radio.utils.*;

import fr.unice.i3s.wimmics.rss.Rss;
import fr.unice.i3s.wimmics.rss.RssChannel;
import fr.unice.i3s.wimmics.rss.RssItem;
import fr.unice.i3s.wimmics.rss.SimpleRssGenerator;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 *
 * @author eamosse
 */
public class Test extends AbstractFacade<Message> {

    public static void main(String[] args) {
//     List<Message> messages = new Test().findAll(); 
//     if(messages!=null)
//         System.out.println("We have " + messages.size()); 
//         else
//         System.out.println("lolllllllllllllllllllll"); 
        Test t = new Test();
        t.findAll();
    }

    private void myTest2() {

//        String query = Utils.prefixes() + "\n"
//                + "select ?feature ?content (count(?content) as ?count)    WHERE\n"
//                + "{\n"
//                + " ?x rdf:type rds:Message. \n"
//                + " ?x rds:frequency ?frequency.\n"
//                + " ?x rds:content ?content.\n"
//                + " ?content rds:text ?text.\n"
//                + " ?x rds:feature ?feature.\n"
//                + " ?feature gn:long_name ?name.\n"
//                + " ?frequency rds:id ?id."
//                + "FILTER (?id=xsd:long(1367329010319))"
//                + "}"
//                + "group by ?frequency ?content ?feature \n"
//                + "ORDER BY ?feature"  ;
        String query = Utils.getAnnotationValue(Message.class, Constant.findByUri, new Object[]{"http://xmlns.com/radiociale/ontology/0.1/Message/1367503466474"});
        // System.out.println(query);
        query = "describe <http://xmlns.com/radiociale/ontology/0.1/Frequency/1367503112574> <http://xmlns.com/radiociale/ontology/0.1/Category/1367502968336>";
        query = Utils.prefixes()
                + "describe ?s ?o where{\n"
                + "{select ?s ?p ?o where {"
                + "{?s ?p ?o. \n"
                + "?s rdf:type rds:Frequency. \n"
                + "?s rds:id ?id. \n"
                + "filter (?id=xsd:long(1367503112262) && ?o!=rdf:type)}\n"
                + "UNION {?s rdf:type rdfs:Class}"
                + "}}"
                + "}";
        
        query = Utils.prefixes() + "describe <http://xmlns.com/radiociale/ontology/0.1/Frequency/1367503112262> "
                + "<http://xmlns.com/radiociale/ontology/0.1/Category/1367502968336>";
//        query = "PREFIX : <http://example/>\n"
//                + "describe ?x\n"
//                + "{ \n"
//                + "  ?x a :Toy .\n"
//                + "  { SELECT ?x ( count(?order) as ?q ) { ?x :order ?order } GROUP BY ?x }\n"
//                + "  FILTER ( ?q > 5 )\n"
//                + "}";
        Query mquery = QueryFactory.create(query, Syntax.syntaxARQ);
System.out.println(mquery);
        mquery.validate();

        VirtGraph graph = this.getGraph();
        
        QueryExecution vqe;
        vqe = VirtuosoQueryExecutionFactory.create(mquery, graph);
        Model m = vqe.execDescribe();
        m.setNsPrefixes(Utils.prefix);
        m.write(System.out);
        //RDF2Bean reader = new  RDF2Bean(m); 
        // Find resource
        // Find properties
//        ResIterator resIterator = m.listSubjects();
//        while (resIterator.hasNext()) {
//            Resource res = resIterator.next();
//            StmtIterator iter = res.listProperties();
//
//            // Print out triple - subject | property | object
//            while (iter.hasNext()) {
//                // Next statement in queue
//                Statement stmt = iter.next();
//                // Get subject, print
//                Resource res2 = stmt.getSubject();
//                System.out.print(res2.getNameSpace() + res2.getLocalName());
//
//                // Get predicate, print
//                Property prop = stmt.getPredicate();
//                System.out.print(" " + prop.getNameSpace() + prop.getLocalName());
//
//                // Get object, print
//                RDFNode node = stmt.getObject();
//                System.out.println(" " + node.toString() + "\n");
//                System.out.println("isresource " + node.isResource());
//                System.out.println("isresource " + node.isURIResource());
//            }
//        }


//       
//       List<Message> results = toList(createQuery(query));
//        if (results != null && !results.isEmpty()) {
//            Message message =  results.get(0);
//            if(message!=null)
//                System.out.println("aaaaaaaaaa " + message.getId());
//        }

//        ResultSet results = vqe.execSelect();
//        Map<Resource, QuerySolution> map = new HashMap<Resource, QuerySolution>();
//        
//        while(results.hasNext()){
//            QuerySolution next = results.next();
//            Resource resource = next.getResource("feature"); 
//            if(!map.containsKey(resource)){
//                map.put(resource, next);
//            }else{
//                QuerySolution savedSolution = map.get(resource); 
//                if(savedSolution.getLiteral("count").getInt() < next.getLiteral("count").getInt())
//                {
//                    map.put(resource, next);
//                }
//            }
//        }
//       Set set = map.keySet();
//       // String results = "";
//        Iterator iter = set.iterator();
//        while (iter.hasNext()) {
//            Resource resource = (Resource) iter.next();
//            System.out.println(resource.toString());
//            System.out.println(map.get(resource).toString());
//        }

        // List<Frequency> findAll1 = frequencyFacade.findAll(); 
        //System.out.println("frequencies " + findAll1.size());
    }


    public Test() {
        super(Message.class);
    }
       public void myTest(long id)
    {
         String query = Utils.prefixes() + "\n"
                + "select ?feature ?content (count(?content) as ?count)    WHERE\n"
                + "{\n"
                + " ?x rdf:type rds:Message. \n"
                + " ?x rds:frequency ?frequency.\n"
                + " ?x rds:content ?content.\n"
                + " ?x rds:feature ?feature.\n"
                + " ?frequency rds:id ?id."
                + "FILTER (?id=xsd:long("+id+"))"
                + "}"
                + "group by ?frequency ?content ?feature \n"
                + "ORDER BY ?feature"  ;
        System.out.println(query);
        
        VirtGraph graph = this.getGraph();
        QueryExecution vqe;
        vqe = VirtuosoQueryExecutionFactory.create(query, graph);
        ResultSet results = vqe.execSelect();
        Map<Resource, QuerySolution> map = new HashMap<Resource, QuerySolution>();
        Frequency f = new TestFrequency().find(id); 
        List<Response> responses = new ArrayList<Response>(); 
        while(results.hasNext()){
            QuerySolution next = results.next();
            Resource resource = next.getResource("feature"); 
            if(!map.containsKey(resource)){
                map.put(resource, next);
            }else{
                QuerySolution savedSolution = map.get(resource); 
                if(savedSolution.getLiteral("count").getInt() < next.getLiteral("count").getInt())
                {
                    map.put(resource, next);
                    
                }
            }
        }
       Set set = map.keySet();
        String res = "";
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
             try { 
            Resource resource = (Resource) iter.next();
            res+=resource.toString() + "    " + map.get(resource).toString();
            QuerySolution sol = map.get(resource); 
            System.out.println("Content --> " + sol.getResource("content")); 
            Response r = f.getTag(sol.getResource("content").toString()); 
            if(r!=null)
                System.out.println("Response ---> " + r.getText());
            MessageFeed feed = new MessageFeed(); 
            feed.setContent(r);
            feed.setFrequency(f);
            feed.setId(System.currentTimeMillis());
            feed.setOccurence(sol.getLiteral("count").getInt());
            Feature fea = new FeatureTest().find(new URI(sol.getResource("feature").toString()));
                 if(fea!=null)
                     feed.setFeature(fea);
                  new MessageFeedTest().create(feed);
                 try {
                     SimpleRssGenerator.generate(createRss(feed), feed.getFrequency().getName()+".rss");
                 } catch (IllegalArgumentException ex) {
                     Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (IllegalAccessException ex) {
                     Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (IOException ex) {
                     Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                 }
             } catch (URISyntaxException ex) {
                 Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
             }
           
        }
//         JsfUtil.addSuccessMessage(res);
        System.out.println("Frequency : " + f.getName() );
       // System.out.println("resultat: " + res);
    } 
       
        private RssItem createRssItem(MessageFeed feed)
	{
		RssItem item = new RssItem();
		
//		EmailAddress haoji = new EmailAddress();
//		haoji.email = "hao.ji@ericsson.com";
//		item.author = haoji;
		
//		Category category = new Category();
//		category.category = "CategoryOne";
//		category.domain = "domain String";
//		item.category = category;
//		
		item.comments = "this is the basic notification";
		item.description = feed.getFeature().toString();
		
//		Enclosure en = new Enclosure();
//		en.enclosure = "enClosure";
//		en.length = 10;
//		en.type = "Type of enclosure";
//		en.url = "http://test.123.com";
//		item.enclosure = en;
		
//		Guid guid = new Guid();
//		guid.guid = "GUID-STRING-TEST-HAOJI";
//		guid.isPermaLink = true;
//		item.guid = guid;

		item.link = "http://test.123.com";
		item.pubDate = new Date();
		item.title = "Accident " + "[" + feed.getContent().getText() + "]";
		
		return item;
	}
	
	private RssChannel createRssChannel(MessageFeed feed)
	{
		RssChannel rssChannel = new RssChannel();

		rssChannel.setTitle(feed.getFrequency().getName());
		rssChannel.setTtl(5);
		rssChannel.setCopyright("copyright");
		rssChannel.setDescription(feed.getFrequency().getDescription());
		rssChannel.itemList = new LinkedList<RssItem>();
		rssChannel.itemList.add(createRssItem(feed));
		return rssChannel;
	}
	private Rss createRss(MessageFeed feed)
	{
		Rss rss = new Rss();
		rss.channel = createRssChannel(feed);
		return rss;
	}
       
       
}
