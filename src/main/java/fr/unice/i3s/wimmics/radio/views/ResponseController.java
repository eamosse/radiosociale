/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.views;

import fr.unice.i3s.wimmics.radio.controller.ResponseFacade;
import fr.unice.i3s.wimmics.radio.model.Frequency;
import fr.unice.i3s.wimmics.radio.model.Response;
import fr.unice.i3s.wimmics.radio.utils.views.utils.JsfUtil;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author edou
 */
@ManagedBean(name = "responseController")
@SessionScoped
public class ResponseController {
    @EJB 
    private ResponseFacade ejb; 
    private Response current;
    private int selectedItemIndex;
    public ResponseController(){
    }

    public Response getCurrent() {
        return current;
    }

    public void setCurrent(Response current) {
        this.current = current;
    }
    
    public String prepareCreate() {
        System.out.println("calling prepare create");
        current = new Response();
        selectedItemIndex = -1;
        return "Create";
    }
    
//     public String create(Frequency frequency) {
//            current.setId(System.currentTimeMillis());
//            ejb.create(current);
//            int lenght = frequency.getTags()==null? 1: frequency.getTags().length+1;
//            Response[] tags = new Response[lenght]; 
//            System.arraycopy(frequency.getTags(), 0, tags, 0, tags.length-1);
//            tags[tags.length] = current; 
//            frequency.setTags(tags);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ResponseCreated"));
//            return prepareCreate();
//    }
    
}
