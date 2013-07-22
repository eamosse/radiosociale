package fr.unice.i3s.wimmics.radio.views;

import fr.unice.i3s.wimmics.radio.controller.*;
import fr.unice.i3s.wimmics.radio.model.*;
import fr.unice.i3s.wimmics.radio.utils.views.utils.*;

import java.io.*;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;
import javax.faces.model.*;
import javax.servlet.ServletContext;
import org.apache.log4j.*;

@ManagedBean(name = "categoryController")
@SessionScoped
public class CategoryController implements Serializable {

    private fr.unice.i3s.wimmics.radio.model.Category current;
    private Map<String, String> descriptors; 
    private DataModel items = null;
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private FrequencyFacade frequencyFacade;
   
    private long id_embouteillage; 
    private long id_restauration; 
    private PaginationHelper pagination;
    private int selectedItemIndex;
    ServletContext ctx;
    

    public CategoryController() {
       ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
    }

    public fr.unice.i3s.wimmics.radio.model.Category getSelected() {
        if (current == null) {
            current = new fr.unice.i3s.wimmics.radio.model.Category();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CategoryFacade getFacade() {
        return categoryFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    try {
                        return getFacade().count();
                    } catch (Exception  e) {
                        Logger.getLogger(CategoryController.class.getName()).log(Level.ERROR, null, e);
                        JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
                        return 0;
                    } 
                }

                @Override
                public DataModel createPageDataModel() {
                    try {
                        return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                    } catch (Exception  e) {
                        Logger.getLogger(CategoryController.class.getName()).log(Level.ERROR, null, e);
                        JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
                        return null;
                    } 
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (fr.unice.i3s.wimmics.radio.model.Category) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new fr.unice.i3s.wimmics.radio.model.Category();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setId(System.currentTimeMillis());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoryCreated"));
            return prepareCreate();
        } catch (Exception  e) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        } 
    }

    public String prepareEdit() {
        current = (fr.unice.i3s.wimmics.radio.model.Category) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoryUpdated"));
            return "View";
        } catch (Exception  e) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        } 
    }

    public String destroy() {
        current = (fr.unice.i3s.wimmics.radio.model.Category) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() throws  IOException {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoryDeleted"));
        } catch (Exception  e) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
        }
    }

    private void updateCurrentItem() throws IOException {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }
    
    public String getDescription(String description){
        if(descriptors==null)
       descriptors = categoryFacade.loadProperties(ctx.getRealPath("/ontologie"));
       return descriptors!=null ? descriptors.get(description) : "undefined";
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }
    
    public List<Frequency> getFrequencies(){
        if(current==null)
          throw  new NullPointerException("No category selected");
        else{
                current.setFrequencys(frequencyFacade.findByCategory(current.getId()));
        }
        return current.getFrequencys();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        try {
            return JsfUtil.getSelectItems(categoryFacade.findAll(), false);
        } catch (Exception  e) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        } 
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        try {
            return JsfUtil.getSelectItems(categoryFacade.findAll(), true);
        } catch (Exception  e) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        } 
    }

    @FacesConverter(forClass = fr.unice.i3s.wimmics.radio.model.Category.class)
    public static class CategoryControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            try {
                if (value == null || value.length() == 0) {
                    return null;
                }
                CategoryController controller = (CategoryController) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "categoryController");
                return controller.categoryFacade.find(getKey(value));
            } catch (Exception  e) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.ERROR, null, e);
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
                return null;
            } 
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof fr.unice.i3s.wimmics.radio.model.Category) {
                fr.unice.i3s.wimmics.radio.model.Category o = (fr.unice.i3s.wimmics.radio.model.Category) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + fr.unice.i3s.wimmics.radio.model.Category.class.getName());
            }
        }
    }
    
    
     
     public Collection<fr.unice.i3s.wimmics.radio.model.Category> getCategories(){
         return categoryFacade.findAll();
     }
}
