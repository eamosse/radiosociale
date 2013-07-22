/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.views;


import fr.unice.i3s.wimmics.radio.controller.*;
import fr.unice.i3s.wimmics.radio.model.*;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import org.primefaces.event.SelectEvent;  
import org.primefaces.model.tagcloud.DefaultTagCloudItem;  
import org.primefaces.model.tagcloud.DefaultTagCloudModel;  
import org.primefaces.model.tagcloud.TagCloudItem;  
import org.primefaces.model.tagcloud.TagCloudModel;

/**
 *
 * @author eamosse
 */
@ManagedBean(name = "testControlleer")
@SessionScoped
public class TestController {
    @EJB
    private FrequencyFacade frequencyFacade;
    @EJB
    private CategoryFacade categoryFacade;
    private long id_embouteillage; 
    private long id_restauration; 
    
    private TagCloudModel model;  
      
    public TestController() {  
        model = new DefaultTagCloudModel();  
        model.addTag(new DefaultTagCloudItem("Transformers", 1));  
        model.addTag(new DefaultTagCloudItem("RIA", "/ui/tagCloud.jsf", 3));  
        model.addTag(new DefaultTagCloudItem("AJAX", 2));  
        model.addTag(new DefaultTagCloudItem("jQuery", "/ui/tagCloud.jsf", 5));  
        model.addTag(new DefaultTagCloudItem("NextGen", 4));  
        model.addTag(new DefaultTagCloudItem("JSF 2.0", "/ui/tagCloud.jsf", 2));  
        model.addTag(new DefaultTagCloudItem("FCB", 5));  
        model.addTag(new DefaultTagCloudItem("Mobile",  3));  
        model.addTag(new DefaultTagCloudItem("Themes", "/ui/tagCloud.jsf", 4));  
        model.addTag(new DefaultTagCloudItem("Rocks", "/ui/tagCloud.jsf", 1));  
    }  
  
    public TagCloudModel getModel() {  
        return model;  
    }  
      
    public void onSelect(SelectEvent event) {  
        TagCloudItem item = (TagCloudItem) event.getObject();  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", item.getLabel());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
    
    public void test()
    {
        System.out.println("test init......."); 
      
             id_embouteillage= System.currentTimeMillis();
             id_restauration = System.currentTimeMillis()+10; 
            // Main m = new Main(); 
             runCategory();
             System.out.println("Id 1 " + id_embouteillage);
              System.out.println("Id 2 " + id_restauration);
             //TestFrequency f = new TestFrequency(); 
             runFrequency();
            //runCategory();
            
            //System.out.println("Categories create'd " + categoryFacade.count()); 
            
            //runFrequency(); 
            //System.out.println("Frequencies created " + frequencyFacade.count());
            //JsfUtil.addSuccessMessage("Categories created " + categoryFacade.count() + " \n " + "Frequencies created " + frequencyFacade.count() );
        
    }
     void runCategory() {
         System.out.println("Running category test......");
            Category embouteillage = new Category();
            embouteillage.setDescription("Decrit les evenements en rapport avec la circulation routier");
            embouteillage.setId(id_embouteillage);
            embouteillage.setName("Circulation");
            embouteillage.setImage("/pictures/image_bus.png");
            categoryFacade.create(embouteillage);
            //Create another category 
            //id_restauration = System.currentTimeMillis(); 
            Category restauration = new Category();
            restauration.setDescription("Decrit les evenements en rapport avec la restauration");
            restauration.setId(id_restauration+1);
            restauration.setName("Restauration");
            restauration.setImage("/pictures/image_food.png");
            categoryFacade.create(restauration);
    }
    
     
     private void runFrequency(){
          System.out.println("Running Frequency test......");
            id_embouteillage= System.currentTimeMillis(); 
            Category circulation = new Category();
            circulation.setDescription("Decrit les evenements en rapport avec la circulation routier");
            circulation.setId(id_embouteillage);
            circulation.setName("Circulation");
            circulation.setImage("/pictures/image_bus.png");
            categoryFacade.create(circulation);
            //Create another category 
            id_restauration = System.currentTimeMillis(); 
            Category restauration = new Category();
            restauration.setDescription("Decrit les evenements en rapport avec la restauration");
            restauration.setId(id_restauration+1);
            restauration.setName("Restauration");
            restauration.setImage("/pictures/image_food.png");
            categoryFacade.create(restauration);
            //Main m = new Main(); 
           // Category circulation  = categoryFacade.find(id_embouteillage); 
           // Category restauration  = categoryFacade.find(id_restauration);
            //System.out.println("Category " + circulation.getName());
//            //Create a frequency congestion in circulmation 
            Frequency f = new Frequency(); 
            f.setCategory(circulation);
            f.setListen(AccessRight.PUBLIC);
            f.setPublish(AccessRight.PUBLIC);
            f.setName("Embouteillage");
            f.setDescription("Description d'embouteillage! Lorem ipsum dolor! Lorem ipsum dolor!Lorem ipsum dolor");
            f.setId(System.currentTimeMillis());
            f.setImage("/pictures/image_bus.png");
            f.setResponseType(ResponseType.SIMPLE_CHOICE);
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
            
            frequencyFacade.create(f);
            
            //            //Create a frequency congestion in circulmation 
             f = new Frequency(); 
            f.setCategory(circulation);
            f.setListen(AccessRight.PUBLIC);
            f.setPublish(AccessRight.PUBLIC);
            f.setName("Accident");
            f.setDescription("Description de la fréquence Accident! Lorem ipsum dolor! Lorem ipsum dolor!Lorem ipsum dolor");
            f.setId(System.currentTimeMillis());
            f.setImage("/pictures/image_accident.jpg");
            f.setResponseType(ResponseType.SIMPLE_CHOICE);
            f.setResponseType(ResponseType.SIMPLE_CHOICE);
            c = new HashSet<Response>();
            c.add(new Response("Très grave", "/pictures/image_accident_major.png"));
            c.add(new Response("Pas trop grave", "/pictures/image_accident_minor.png"));
            f.setTopic("Un accident sur la route! Alors dites le nous!");
            f.setTags(c);
            a = new Administrator(); 
            a.setEmail("test@exemple.com");
            a.setFirstName("Administrator");
            a.setLastName("Test");
            a.setId(System.currentTimeMillis());
            a.setNick("eamosse");
            a.setId(System.currentTimeMillis());
            f.setAdministrator(a);
            
            frequencyFacade.create(f);
            
            f = new Frequency(); 
            f.setCategory(circulation);
            f.setListen(AccessRight.PUBLIC);
            f.setPublish(AccessRight.PUBLIC);
            f.setName("Radar");
            f.setId(System.currentTimeMillis());
            f.setImage("/pictures/image_radar.png");
            f.setDescription("Description de la frequence Radar! Lorem ipsum dolor! Lorem ipsum dolor!Lorem ipsum dolor");
            f.setResponseType(ResponseType.SIMPLE_CHOICE);
            c = new HashSet<Response>();
            c.add(new Response("Mobile", "/pictures/image_radar.png"));
            c.add(new Response("Fixe", "/pictures/image_radar_fixe.png"));
            f.setTags(c);
            f.setTopic("Signaler un radar!");
            f.setAdministrator(a);
            frequencyFacade.create(f);
            
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
            frequencyFacade.create(an_restaurant);
    }
}
