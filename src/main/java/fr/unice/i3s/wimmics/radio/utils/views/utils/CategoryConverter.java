/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.utils.views.utils;

import fr.unice.i3s.wimmics.radio.controller.AbstractFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import  fr.unice.i3s.wimmics.radio.model.Category;

/**
 *
 * @author edou
 */
@FacesConverter(value="category")
public class CategoryConverter  extends AbstractFacade<Category> implements Converter{
    //CategoryFacade categoryFacade = lookupCategoryFacadeBean();
  
    
    public CategoryConverter(){
        super(Category.class);
    }
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
     //  Category c = categoryFacade.find(Long.valueOf(value));
        System.out.println("value " + value);
        return this.find(Long.parseLong(value));
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof Category)
            return String.valueOf(((Category)value).getId());
        else
            return "Unknow type";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
