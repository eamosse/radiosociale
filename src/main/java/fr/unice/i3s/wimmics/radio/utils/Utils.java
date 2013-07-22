/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.utils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import fr.unice.i3s.wimmics.radio.model.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import thewebsemantic.Bean2RDF;

/**
 *
 * @author eamosse
 */
public class Utils {

    public static final Map<String, String> prefix = new HashMap<String, String>();
    public static ArrayList<Class> primitives = new ArrayList<Class>();
    public static final String selectAllForlmat = " select * where {"
            + "{?x ?o ?t. FILTER (?x =rds:Class)}"
            + "}";
    public static final String selectById =
            " select ?x ?id  \n "
            + "WHERE\n"
            + "{\n"
            + "{\n"
            + "?x rdf:type rds:%s. \n"
            + "?x rds:id ?id. \n"
            + "FILTER (?id=xsd:long(%s)) \n"
            + "}\n"
            + "}\n";
    public static final String selectByUri =
            " select ?x ?id  \n "
            + "WHERE\n"
            + "{\n"
            + "{\n"
            + "?x rdf:type rds:%s. \n"
            + "?x rds:id ?id. \n"
            + "FILTER (?id=xsd:long(%s)) \n"
            + "}\n"
            + "}\n";
    public static final String selectWithLimit =
            " select ?x ?id  \n "
            + "WHERE\n"
            + "{\n"
            + "{\n"
            + "?x rdf:type rds:%s. \n"
            + "?x rds:id ?id. \n"
            + "}\n"
            + "}\n";

    static {
        prefix.put("vcard", Constant.VCARD);
        prefix.put("foaf", Constant.FOAF);
        prefix.put("", Constant.NS);
        prefix.put("gn", Constant.GEONAMES);
        prefix.put("wgs", Constant.POSITION);
        prefix.put("time", Constant.TIME);
        prefix.put("ws", Constant.WEBSEMENTIC);
        prefix.put("rdf", Constant.RDF);
        prefix.put("rdfs", Constant.RDFS);
        prefix.put("xsd", Constant.XSD);

        //set primitives type 
    };
    
    
    public static String prefixes() {
        Set set = prefix.keySet();
        String results = "";
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String next = (String) iter.next();
            if (!next.equalsIgnoreCase("")) {
                results += "PREFIX " + next + ": <" + Utils.prefix.get(next) + ">\n";
            } else {
                results += "PREFIX rds: <" + Utils.prefix.get(next) + ">\n";
            }
        }
        return results;
    }

    public static boolean isWrapperType(Class<?> clazz) {
        return clazz.equals(Boolean.class)
                || clazz.equals(Integer.class)
                || clazz.equals(Character.class)
                || clazz.equals(Byte.class)
                || clazz.equals(Short.class)
                || clazz.equals(Double.class)
                || clazz.equals(Long.class)
                || clazz.equals(String.class)
                || clazz.equals(Float.class);
    }

    public static Model completModel(Class<?> clazz) {
        Model m = ModelFactory.createDefaultModel();
        Bean2RDF writer = new Bean2RDF(m);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            boolean prim = Utils.isWrapperType(field.getType());
            if (!prim && !field.getType().isArray() && !Collection.class.isAssignableFrom(field.getType()) && !field.getType().isEnum()) {
                try {
                    System.out.println(name);
                    writer.save(field.getType().newInstance());
                } catch (InstantiationException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        writer.save(AccessRight.AUTHENTICATED_ONLY);
        writer.save(ResponseType.MULTIPLE_CHOICE);
        writer.save(new Response());
        return m;
    }

    public static String getAnnotationValue(Class clazz, String annotationName, Object params[]) {

        Annotation an = clazz.getAnnotation(SparqlQueries.class);
        if (an != null) {
            SparqlQueries ann = (SparqlQueries) an;
            SparqlAnnotation[] values = ann.value();
            for (SparqlAnnotation myAnnotation : values) {
                if (myAnnotation.name().equalsIgnoreCase(annotationName)) {
                    String res= Utils.prefixes() + myAnnotation.value();
                    return (params == null || params.length==0)?  res : String.format(res, params);
                }
            }
        }
        return "";
    }
}
