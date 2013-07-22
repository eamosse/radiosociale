package fr.unice.i3s.wimmics.radio.views;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Resource;
import fr.unice.i3s.wimmics.radio.controller.*;
import fr.unice.i3s.wimmics.radio.model.*;
import fr.unice.i3s.wimmics.radio.utils.*;
import fr.unice.i3s.wimmics.radio.utils.views.utils.*;

import fr.unice.i3s.wimmics.rss.*;
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
import virtuoso.jena.driver.*;

@ManagedBean(name = "messageController")
@SessionScoped
public class MessageController implements Serializable {
    @EJB
    private FrequencyFacade frequencyFacade1;

    @EJB
    private MessageFeedFacade messageFeedFacade;
    @EJB
    private FrequencyFacade frequencyFacade;
    @EJB
    private FeatureFacade featureFacade;
    private Message current;
    private DataModel items = null;
    @EJB
    private MessageFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public MessageController() {
    }

    public Message getSelected() {
        if (current == null) {
            current = new Message();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MessageFacade getFacade() {
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
                        Logger.getLogger(MessageController.class.getName()).log(Level.ERROR, null, e);
                        JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
                        return 0;
                    }
                }

                @Override
                public DataModel createPageDataModel() {
                    try {
                        return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                    } catch (Exception e) {
                        Logger.getLogger(MessageController.class.getName()).log(Level.ERROR, null, e);
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
        current = (Message) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Message();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setId(System.currentTimeMillis());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MessageCreated"));
            return prepareCreate();
        } catch (Exception e) {
            Logger.getLogger(MessageController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Message) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MessageUpdated"));
            return "View";
        } catch (Exception e) {
            Logger.getLogger(MessageController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Message) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MessageDeleted"));
        } catch (Exception e) {
            Logger.getLogger(MessageController.class.getName()).log(Level.ERROR, null, e);
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
        } catch (Exception e) {
            Logger.getLogger(MessageController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        }
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        try {
            return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        } catch (Exception e) {
            Logger.getLogger(MessageController.class.getName()).log(Level.ERROR, null, e);
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("IOErrorOccured"));
            return null;
        }
    }

    @FacesConverter(forClass = Message.class)
    public static class MessageControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            try {
                if (value == null || value.length() == 0) {
                    return null;
                }
                MessageController controller = (MessageController) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "messageController");
                return controller.ejbFacade.find(getKey(value));
            } catch (Exception e) {
                Logger.getLogger(MessageController.class.getName()).log(Level.ERROR, null, e);
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
            if (object instanceof Message) {
                Message o = (Message) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Message.class.getName());
            }
        }
    }

    public void myTest() {
        List<Frequency> findAll = frequencyFacade.findAll();
        for(Frequency f: findAll)
            runIt(f);
    }
    ServletContext ctx;
    public void runIt(Frequency f){
          ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        File fFF = new File(realPath + "file2.rss"); 
        File ff = new File(ctx.getContextPath() + "file1.rss");
        System.out.println("Path " + realPath);
        String query = Utils.prefixes() + "\n"
                + "select ?feature ?content (count(?content) as ?count)    WHERE\n"
                + "{\n"
                + " ?x rdf:type rds:Message. \n"
                + " ?x rds:frequency ?frequency.\n"
                + " ?x rds:content ?content.\n"
                + " ?x rds:feature ?feature.\n"
                + " ?frequency rds:id ?id."
                + "FILTER (?id=xsd:long(" + f.getId() + "))"
                + "}"
                + "group by ?frequency ?content ?feature \n"
                + "ORDER BY ?feature";
        System.out.println(query);

        VirtGraph graph = ejbFacade.getGraph();
        QueryExecution vqe;
        vqe = VirtuosoQueryExecutionFactory.create(query, graph);
        ResultSet results = vqe.execSelect();
        Map<Resource, QuerySolution> map = new HashMap<Resource, QuerySolution>();
        //Frequency f = frequencyFacade.find(id);
        List<Response> responses = new ArrayList<Response>();
        while (results.hasNext()) {
            QuerySolution next = results.next();
            Resource resource = next.getResource("feature");
            if (!map.containsKey(resource)) {
                map.put(resource, next);
            } else {
                QuerySolution savedSolution = map.get(resource);
                if (savedSolution.getLiteral("count").getInt() < next.getLiteral("count").getInt()) {
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
                res += resource.toString() + "    " + map.get(resource).toString();
                QuerySolution sol = map.get(resource);
                System.out.println("Content --> " + sol.getResource("content"));
                Response r = f.getTag(sol.getResource("content").toString());
                if (r != null) {
                    System.out.println("Response ---> " + r.getText());
                }
                MessageFeed feed = new MessageFeed();
                feed.setContent(r);
                feed.setFrequency(f);
                feed.setId(System.currentTimeMillis());
                feed.setOccurence(sol.getLiteral("count").getInt());
                Feature fea = featureFacade.find(sol.getResource("feature").toString());
                if (fea != null) {
                    feed.setFeature(fea);
                }
                messageFeedFacade.create(feed);
                try {
                    SimpleRssGenerator.generate(createRss(feed), realPath+feed.getFrequency().getName() + ".rss");
                } catch (IllegalArgumentException ex) {
                    java.util.logging.Logger.getLogger(MessageController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(MessageController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(MessageController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(MessageController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

        }
//         JsfUtil.addSuccessMessage(res);
        System.out.println("Frequency : " + f.getName());
        // System.out.println("resultat: " + res);
    }

    private RssItem createRssItem(MessageFeed feed) {
        RssItem item = new RssItem();

//		EmailAddress haoji = new EmailAddress();
//		haoji.email = "hao.ji@ericsson.com";
//		item.author = haoji;

//		Category category = new Category();
//		category.category = "CategoryOne";
//		category.domain = "domain String";
//		item.category = category;
//		
        item.comments = feed.getFeature().toString();
        item.description = feed.getFeature().getName();

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

        item.link =  ctx.getContextPath() + feed.getContent().getImage();
        item.pubDate = new Date();
        item.title = feed.getContent().getText();

        return item;
    }

    private RssChannel createRssChannel(MessageFeed feed) {
        RssChannel rssChannel = new RssChannel();
        rssChannel.setTitle(feed.getFrequency().getName());
        rssChannel.setTtl(5);
        rssChannel.setCopyright("copyright");
        rssChannel.setDescription(feed.getFrequency().getDescription());
        rssChannel.itemList = new LinkedList<RssItem>();
        rssChannel.itemList.add(createRssItem(feed));
        return rssChannel;
    }

    private Rss createRss(MessageFeed feed) {
        Rss rss = new Rss();
        rss.channel = createRssChannel(feed);
        return rss;
    }
}
