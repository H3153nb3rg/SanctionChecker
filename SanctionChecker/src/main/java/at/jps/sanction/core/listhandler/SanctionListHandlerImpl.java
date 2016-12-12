package at.jps.sanction.core.listhandler;

import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.io.db.DBHelper;
import at.jps.sanction.core.util.string.LuceneIndex;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.wl.entities.SL_Entry;
import at.jps.sanction.model.wl.entities.WL_Entity;

public abstract class SanctionListHandlerImpl extends BaseFileHandler implements SanctionListHandler {

    static final Logger                 logger            = LoggerFactory.getLogger(SanctionListHandlerImpl.class);

    private String                      delimiters        = " ";
    private String                      deadCharacters    = "";
    private String                      listType          = "";
    private String                      listCategory      = "";

    private String                      description;
    private int                         orderId           = 0;
    private int                         severity          = 0;

    private boolean                     fuzzySearch       = false;
    private boolean                     fourEyesPrinciple = false;
    private boolean                     useSysProxy       = true;

    boolean                             loadWeak          = false;
    boolean                             loadNonPrimary    = true;

    private String                      httpUser;
    private String                      httpPwd;

    private List<WL_Entity>             entityList;

    private Map<String, WL_Entity>      entityListSortedById;

    private List<String>                searchListNames;

    private Map<String, List<SL_Entry>> searchLists;

    // ----------------------
    // ----- search settings

    // <!-- shorter tokens are not checked -->
    int                                 minTokenLen       = 2;

    // <!-- min % match of single token to count as hit -->
    int                                 minRelVal         = 79;
    int                                 minAbsVal         = 60;

    // <!-- % * minTokenlen == Threshold ( fuzzy abort) -->
    double                              fuzzyVal          = 20;

    // ----------------------

    protected void archiveFile(final String filename, final String targetDir, final String targetfilename) {

        if ((filename != null) && (targetDir != null) && (targetfilename != null)) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");

            final String targetfile = targetDir + File.separator + sdf.format(new Date()) + "-" + targetfilename;

            try {
                FileUtils.copyFile(new File(filename), new File(targetfile));

                if (logger.isDebugEnabled()) {
                    logger.debug("(" + getListName() + ") Copy File : [" + filename + "] to [" + targetfilename + "]");
                }
            }
            catch (final IOException e) {

                if (logger.isErrorEnabled()) {
                    logger.error("Copy File : [" + filename + "] to [" + targetfilename + "] failed!!");
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("Exception : ", e);
                }
            }
        }
    }

    static class MyHTTPAuthenticator extends Authenticator {

        String username;
        String pwd;

        public MyHTTPAuthenticator(final String username, final String pwd) {
            super();
            this.username = username;
            this.pwd = pwd;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            // I haven't checked getRequestingScheme() here, since for NTLM
            // and Negotiate, the usrname and password are all the same.
            System.err.println("Feeding username and password for " + getRequestingScheme());
            return (new PasswordAuthentication(username, pwd.toCharArray()));
        }
    }

    protected String downloadFile(final String url, final boolean useSysProxy) {

        File targetfile = null;

        if (useSysProxy) {

            System.setProperty("java.net.useSystemProxies", "true");

            if (logger.isDebugEnabled()) {
                logger.debug("sysproxy: " + useSysProxy);
            }
        }
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            targetfile = File.createTempFile(getListName() + sdf.format(new Date()), ".dat");
            targetfile.deleteOnExit();

            // if usr/pwd is set just do
            if ((getHttpUser() != null) && (getHttpPwd() != null)) {
                Authenticator.setDefault(new MyHTTPAuthenticator(getHttpUser(), getHttpPwd()));
            }

            FileUtils.copyURLToFile(new URL(url), targetfile);

            if (logger.isDebugEnabled()) {
                logger.debug("(" + getListName() + ") downloaded from URL: " + url + " to " + targetfile.getAbsolutePath());
            }

        }
        catch (final Exception x) {
            if (logger.isErrorEnabled()) {
                logger.error("download from  : [" + url + "] failed!!");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Exception : ", x);
            }
        }

        return (targetfile == null) ? null : targetfile.getAbsolutePath();
    }

    @Override
    public String getDeadCharacters() {
        return deadCharacters;
    }

    @Override
    public String getDelimiters() {
        return delimiters;
    }

    @Override
    public int getOrderNr() {
        return orderId;
    }

    @Override
    public int getSeverity() {
        return severity;
    }

    @Override
    public List<String> getStopwordList() {
        logger.error("Stopword list for (" + getListName() + ") not yet implemented!");
        return null;
    }

    @Override
    public String getType() {
        return listType;
    }

    @Override
    public void initialize() {

        // String settings = properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".delimiters", " ");
        //
        // delimiters += ","; // TODO add colon to delimiters !!!!!

        // settings = properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".deadchars.AT", "."); // TODO: hardcoded lang!

        if (logger.isDebugEnabled()) {
            logger.debug("Delimiters  (" + getListName() + ") : " + delimiters);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Dead Characters  (" + getListName() + ") : " + deadCharacters);
        }

        // listType = properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".type", "UNKNONWN");

        // fuzzySearch = Boolean.parseBoolean(properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".fuzzysearch", "false"));

        // fourEyesPrinciple = Boolean.parseBoolean(properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".4eyesprinciple", "false"));

    }

    @Override
    public void initDone() {
        // at this point the lists are loaded - now we start with our post processing

        // now we try to add this stuff to the lucy index

        final LuceneIndex li = new LuceneIndex();

        for (final String topicName : getSearchListNames()) {

            if (getSearchLists().get(topicName) != null) {
                li.buildIndex(topicName, getSearchLists().get(topicName));
            }
        }
    }

    @Override
    public void setOrderNr(final int orderId) {

        this.orderId = orderId;
    }

    @Override
    public void setSeverity(final int severity) {
        this.severity = severity;
    }

    @Override
    public boolean isFuzzySearch() {
        return fuzzySearch;
    }

    @Override
    public String getListDescription() {
        return description;
    }

    @Override
    public boolean isFourEyesPrinciple() {
        return fourEyesPrinciple;
    }

    public void setFourEyesPrinciple(final boolean foureyesprincipleEnabled) {
        fourEyesPrinciple = foureyesprincipleEnabled;
    }

    public void setDelimiters(final String delimiters) {

        this.delimiters = "";
        final StringTokenizer tokenizer = new StringTokenizer(delimiters, ",");

        while (tokenizer.hasMoreTokens()) {
            this.delimiters += tokenizer.nextToken(); // ,
        }
    }

    public void setDeadCharacters(final String deadCharacters) {

        this.deadCharacters = "";
        final StringTokenizer tokenizer = new StringTokenizer(deadCharacters, ",");

        while (tokenizer.hasMoreTokens()) {
            this.deadCharacters += tokenizer.nextToken();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isUseSysProxy() {
        return useSysProxy;
    }

    public void setUseSysProxy(final boolean useSysProxy) {
        this.useSysProxy = useSysProxy;
    }

    public boolean isLoadWeak() {
        return loadWeak;
    }

    public void setLoadWeak(final boolean loadWeak) {
        this.loadWeak = loadWeak;
    }

    public boolean isLoadNonPrimary() {
        return loadNonPrimary;
    }

    public void setLoadNonPrimary(final boolean loadNonPrimary) {
        this.loadNonPrimary = loadNonPrimary;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(final String listType) {
        this.listType = listType;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(final int orderId) {
        this.orderId = orderId;
    }

    public void setFuzzySearch(final boolean fuzzySearch) {
        this.fuzzySearch = fuzzySearch;
    }

    protected Map<String, WL_Entity> getEntityListSortedById() {

        if (entityListSortedById == null) {
            entityListSortedById = new HashMap<>();
        }

        return entityListSortedById;
    }

    protected void setEntityListSortedById(final HashMap<String, WL_Entity> entityListSortedById) {
        this.entityListSortedById = entityListSortedById;
    }

    public void addWLEntry(final WL_Entity entity) {

        entity.setListName(getListName());

        getEntityList().add(entity);
        getEntityListSortedById().put(entity.getWL_Id(), entity);

        // split to simple core searchlists
        for (final String topicName : getSearchListNames()) {

            try {
                final List<SL_Entry> slEntries = buildSearchListEntries(topicName, entity);

                if (slEntries != null) {

                    for (final SL_Entry slEntry : slEntries) {

                        slEntry.setReferencedEntity(entity);
                        try {

                            List<SL_Entry> searchList = getSearchLists().get(topicName);

                            if (searchList == null) {
                                searchList = new ArrayList<>();
                                getSearchLists().put(topicName, searchList);
                            }

                            searchList.add(slEntry);
                        }
                        catch (final Exception x) {
                            System.err.println("Configuration error add WLEntry TopicName: " + topicName + " to List " + getListName());
                            logger.error("Configuration error add WLEntry TopicName: " + topicName + " to List " + getListName(), x);
                        }
                    }
                }
            }
            catch (final Exception x) {
                System.err.println("Configuration error: TopicName: " + topicName + " to List " + getListName());
                logger.error("Configuration error: TopicName: " + topicName + " to List " + getListName(), x);

            }
        }
    }

    public abstract List<SL_Entry> buildSearchListEntries(final String topicName, WL_Entity entity);

    @Override
    public WL_Entity getEntityById(final String wl_id) {
        return getEntityListSortedById().get(wl_id);
    }

    @Override
    public List<WL_Entity> getEntityList() {

        if (entityList == null) {
            entityList = new ArrayList<>();
        }

        return entityList;
    }

    public void setEntityList(final List<WL_Entity> entityList) {
        this.entityList = entityList;
    }

    public String getHttpPwd() {
        return httpPwd;
    }

    public void setHttpPwd(final String httpPwd) {
        this.httpPwd = httpPwd;
    }

    public String getHttpUser() {
        return httpUser;
    }

    public void setHttpUser(final String httpUser) {
        this.httpUser = httpUser;
    }

    public void saveToDB(final Connection connection) {
        final DBHelper dbh = new DBHelper();
        dbh.setConnection(connection);

        for (final WL_Entity entity : getEntityList()) {
            dbh.save_WL_Entity(entity, true);
        }
    }

    @Override
    public String getListCategory() {
        return listCategory;
    }

    public void setListCategory(String listCategory) {
        this.listCategory = listCategory;
    }

    public List<String> getSearchListNames() {
        return searchListNames;
    }

    public void setSearchListNames(List<String> searchListNames) {
        this.searchListNames = searchListNames;
    }

    @Override
    public Map<String, List<SL_Entry>> getSearchLists() {

        if (searchLists == null) {
            searchLists = new LinkedHashMap<>();
        }
        return searchLists;
    }

    public void setSearchLists(HashMap<String, List<SL_Entry>> searchLists) {
        this.searchLists = searchLists;
    }

    @Override
    public int getMinTokenLen() {
        return minTokenLen;
    }

    @Override
    public void setMinTokenLen(int minTokenLen) {
        this.minTokenLen = minTokenLen;
    }

    @Override
    public int getMinRelVal() {
        return minRelVal;
    }

    @Override
    public void setMinRelVal(int minRelVal) {
        this.minRelVal = minRelVal;
    }

    @Override
    public int getMinAbsVal() {
        return minAbsVal;
    }

    @Override
    public void setMinAbsVal(int minAbsVal) {
        this.minAbsVal = minAbsVal;
    }

    @Override
    public double getFuzzyVal() {
        return fuzzyVal;
    }

    @Override
    public void setFuzzyVal(int fuzzyVal) {
        this.fuzzyVal = fuzzyVal / 100;
    }

}
