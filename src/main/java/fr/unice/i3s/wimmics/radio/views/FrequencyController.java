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
import javax.faces.event.ActionEvent;
import javax.faces.model.*;
import org.apache.log4j.*;
import org.primefaces.context.RequestContext;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;
import org.primefaces.event.SelectEvent;  
import org.primefaces.model.tagcloud.DefaultTagCloudItem;  
import org.primefaces.model.tagcloud.DefaultTagCloudModel;  
import org.primefaces.model.tagcloud.TagCloudItem;  
import org.primefaces.model.tagcloud.TagCloudModel;

@ManagedBean(name = "frequencyController")
@SessionScoped
public class FrequencyController implements Serializable {

    @EJB
    private ResponseFacade responseFacade;
    private Frequency current;
    private DataModel items = null;
    @EJB
    private FrequencyFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public FrequencyController() {
    }

    public Frequency getSelected() {
        if (current == null) {
            current = new Frequency();
            selectedItemIndex = -1;
            currentResponse = new Response();
        }
        return current;
    }

    private FrequencyFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    try {
                        return getFacade().count();
                    } catch (Exception e) {
                        Logger.getLogger(FrequencyController.class.getName()).log(Level.ERROR, null, e);
                        JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
                        return 0;
                    }
                }

                @Override
                public DataModel createPageDataModel() {
                    try {
                        return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                    } catch (Exception e) {
                        Logger.getLogger(FrequencyController.class.getName()).log(Level.ERROR, null, e);
                        JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
                        return null;
                    }
                }
            };
        }
        return pagination;
    }
    
    private TagCloudModel model;  
      
  
    public TagCloudModel getModel() {  
         model = new DefaultTagCloudModel(); 
         if(getTags()!=null)
        for( Response tag : getTags()) {
            model.addTag(new DefaultTagCloudItem(tag.getText(), 4));
        }
        return model;  
    }  

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Frequency) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Frequency();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setId(System.currentTimeMillis());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FrequencyCreated"));
            return "View";
        } catch (Exception e) {
            Logger.getLogger(FrequencyController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Frequency) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FrequencyUpdated"));
            return "View";
        } catch (Exception e) {
            Logger.getLogger(FrequencyController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Frequency) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() throws IOException {
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FrequencyDeleted"));
        } catch (Exception e) {
            Logger.getLogger(FrequencyController.class.getName()).log(Level.ERROR, null, e);
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
        //items = getPagination().createPageDataModel();
        return items;
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

    public SelectItem[] getItemsAvailableSelectMany() {
        try {
            return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        } catch (Exception e) {
            Logger.getLogger(FrequencyController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        }
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        try {
            return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        } catch (Exception e) {
            Logger.getLogger(FrequencyController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        }
    }

    @FacesConverter(forClass = Frequency.class)
    public static class FrequencyControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            try {
                if (value == null || value.length() == 0) {
                    return null;
                }
                FrequencyController controller = (FrequencyController) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "frequencyController");
                return controller.ejbFacade.find(getKey(value));
            } catch (Exception e) {
                Logger.getLogger(FrequencyController.class.getName()).log(Level.ERROR, null, e);
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
            if (object instanceof Frequency) {
                Frequency o = (Frequency) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Frequency.class.getName());
            }
        }
    }

    public SelectItem[] getAccessValues() {
        //return JsfUtil.getSelectItems(AccessRight.values(), false);
        SelectItem[] _items = new SelectItem[AccessRight.values().length];
        int i = 0;
        for (AccessRight g : AccessRight.values()) {
            _items[i++] = new SelectItem(g, g.name());
        }
        return _items;
    }

    public SelectItem[] getResponseType() {
        SelectItem[] _items = new SelectItem[ResponseType.values().length];
        int i = 0;
        for (ResponseType g : ResponseType.values()) {
            _items[i++] = new SelectItem(g, g.name());
        }
        return _items;
    }

    public Response getCurrentResponse() {
        if (currentResponse == null) {
            currentResponse = new Response();
        }
        return currentResponse;
    }

    public void setCurrentResponse(Response currentResponse) {
        this.currentResponse = currentResponse;
    }
    private Response currentResponse;

    public String prepareCreate_Tag() {
        System.out.println("calling prepare create");
        currentResponse = new Response();
        return "Create_Tag";
    }

    public void createTag() {
         RequestContext context = RequestContext.getCurrentInstance();  
         boolean add =true; 
         String message; 
        if (current == null) {
            add= false; 
            message= "Ooooops! It seems that the frequency is null"; 
        }
        else{
        currentResponse.setId(System.currentTimeMillis());
        responseFacade.create(currentResponse);
        if (current.getTags() == null) {
            current.setTags(new HashSet<Response>());
        }
        Iterator<Response> iterator = current.getTags().iterator();
        while (iterator.hasNext()) {
            System.out.println("Response " + iterator.next().getText());
        }
        current.getTags().add(currentResponse);
        getFacade().edit(current);
        
        message = "The tag is succesfully added to the frequency!"; 
        }
        context.addCallbackParam("added", add);
        JsfUtil.addSuccessMessage(message);
        currentResponse= new Response(); 
    }
    
    public List<Response> getTags(){
        if(getSelected().getTags()==null)
            getSelected().setTags(new ArrayList<Response>());
        ArrayList<Response> arrayList = new ArrayList<Response>(getSelected().getTags());
        System.out.println("Size " + arrayList.size() + " Size " + getSelected().getTags().size());
        return arrayList;
    }
    
   
}
