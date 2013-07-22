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
import org.apache.log4j.*;

@ManagedBean(name = "administratorController")
@SessionScoped
public class AdministratorController implements Serializable {

    private Administrator current;
    private DataModel items = null;
    @EJB
    private AdministratorFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AdministratorController() {
    }

    public Administrator getSelected() {
        if (current == null) {
            current = new Administrator();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AdministratorFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    try {
                        return getFacade().count();
                    } catch (Exception  e) {
                        Logger.getLogger(AdministratorController.class.getName()).log(Level.ERROR, null, e);
                        JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
                        return 0;
                    } 
                }

                @Override
                public DataModel createPageDataModel() {
                    try {
                        return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                    } catch (Exception  e) {
                        Logger.getLogger(AdministratorController.class.getName()).log(Level.ERROR, null, e);
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
        current = (Administrator) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Administrator();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setId(System.currentTimeMillis());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AdministratorCreated"));
            return prepareCreate();
        } catch (Exception  e) {
            Logger.getLogger(AdministratorController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        } 
    }

    public String prepareEdit() {
        current = (Administrator) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AdministratorUpdated"));
            return "View";
        } catch (Exception  e) {
            Logger.getLogger(AdministratorController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        } 
    }

    public String destroy() {
        current = (Administrator) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AdministratorDeleted"));
        } catch (Exception  e) {
            Logger.getLogger(AdministratorController.class.getName()).log(Level.ERROR, null, e);
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
        } catch (Exception  e) {
            Logger.getLogger(AdministratorController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        } 
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        try {
            return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        } catch (Exception  e) {
            Logger.getLogger(AdministratorController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        } 
    }

    @FacesConverter(forClass = Administrator.class)
    public static class AdministratorControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            try {
                if (value == null || value.length() == 0) {
                    return null;
                }
                AdministratorController controller = (AdministratorController) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "administratorController");
                return controller.ejbFacade.find(getKey(value));
            } catch (Exception  e) {
                Logger.getLogger(AdministratorController.class.getName()).log(Level.ERROR, null, e);
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
            if (object instanceof Administrator) {
                Administrator o = (Administrator) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Administrator.class.getName());
            }
        }
    }
}
