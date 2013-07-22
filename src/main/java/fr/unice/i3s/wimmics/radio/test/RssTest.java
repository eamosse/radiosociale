/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.test;


import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import fr.unice.i3s.wimmics.radio.utils.Constant;
import fr.unice.i3s.wimmics.rss.Category;
import fr.unice.i3s.wimmics.rss.EmailAddress;
import fr.unice.i3s.wimmics.rss.Enclosure;
import fr.unice.i3s.wimmics.rss.Guid;
import fr.unice.i3s.wimmics.rss.Rss;
import fr.unice.i3s.wimmics.rss.RssChannel;
import fr.unice.i3s.wimmics.rss.RssItem;
import fr.unice.i3s.wimmics.rss.SimpleRssGenerator;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author eamosse
 */
public class RssTest {

    final static String BASE = Constant.RSS;

    public static void main(String[] args) {


        Rss rss = new RssTest().createRss();

        try {
            //SimpleRssGenerator.generate(rss, new FileOutputStream("rss.xml"));

            SimpleRssGenerator.generate(rss, "rss.xml");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //05/2015
        //060
        //
    }

    private static Resource r(String base, String localname) {
        return ResourceFactory.createResource(base + localname);
    }

    private static Property p(String base, String localname) {
        return ResourceFactory.createProperty(base, localname);
    }

    private static Literal l(Object value) {
        return ResourceFactory.createTypedLiteral(value);
    }

    private static Literal l(String lexicalform, RDFDatatype datatype) {
        return ResourceFactory.createTypedLiteral(lexicalform, datatype);
    }

   private RssItem createRssItem()
	{
		RssItem item = new RssItem();
		
		EmailAddress haoji = new EmailAddress();
		haoji.email = "hao.ji@ericsson.com";
		item.author = haoji;
		
		Category category = new Category();
		category.category = "CategoryOne";
		category.domain = "domain String";
		item.category = category;
		
		item.comments = "This is comments for this item";
		item.description = "This is the description description";
		
		Enclosure en = new Enclosure();
		en.enclosure = "enClosure";
		en.length = 10;
		en.type = "Type of enclosure";
		en.url = "http://test.123.com";
		item.enclosure = en;
		
		Guid guid = new Guid();
		guid.guid = "GUID-STRING-TEST-HAOJI";
		guid.isPermaLink = true;
		item.guid = guid;

		item.link = "http://test.123.com";
		item.pubDate = new Date();
		item.title = "This is title for the item sdsdsdsdsdsdsdsdsd";
		
		return item;
	}
	
	private RssChannel createRssChannel()
	{
		RssChannel rssChannel = new RssChannel();

		rssChannel.setTitle("The title");
		rssChannel.setTtl(5);
		rssChannel.setCopyright("copyright");
		rssChannel.setDescription("description");
		rssChannel.itemList = new LinkedList<RssItem>();
		rssChannel.itemList.add(createRssItem());
		return rssChannel;
	}
	private Rss createRss()
	{
		Rss rss = new Rss();
		rss.channel = createRssChannel();
		return rss;
	}
	public void testGenerate() {
		
		// Basicly this is a demo to show how to use this simple API to create an rss xml file 
		Rss rss = createRss();
		
		try {
			//SimpleRssGenerator.generate(rss, new FileOutputStream("rss.xml"));
			
			SimpleRssGenerator.generate(rss, "rss4.xml");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
