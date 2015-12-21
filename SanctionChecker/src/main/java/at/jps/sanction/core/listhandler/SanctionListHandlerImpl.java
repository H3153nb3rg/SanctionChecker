package at.jps.sanction.core.listhandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.listhandler.SanctionListHandler;

public abstract class SanctionListHandlerImpl extends BaseFileHandler implements SanctionListHandler {

    static final Logger logger            = LoggerFactory.getLogger(SanctionListHandlerImpl.class);

    private String      delimiters        = " ";
    private String      deadCharacters    = "";
    private String      listType          = "";
    private String      description;
    private int         orderId           = 0;
    private int         severity          = 0;

    private boolean     fuzzySearch       = false;
    private boolean     fourEyesPrinciple = false;
    private boolean     useSysProxy       = true;

    boolean             loadWeak          = false;
    boolean             loadNonPrimary    = true;

    protected void archiveFile(final String filename, final String targetDir, final String targetfilename) {
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

    public String getListDescription() {
        return description;
    }

    @Override
    public boolean isFourEyesPrinciple() {
        return fourEyesPrinciple;
    }

    public void setFourEyesPrinciple(boolean foureyesprincipleEnabled) {
        this.fourEyesPrinciple = foureyesprincipleEnabled;
    }

    public void setDelimiters(String delimiters) {

        this.delimiters = "";
        StringTokenizer tokenizer = new StringTokenizer(delimiters, ",");

        while (tokenizer.hasMoreTokens()) {
            this.delimiters += tokenizer.nextToken(); // ,
        }
    }

    public void setDeadCharacters(String deadCharacters) {

        this.deadCharacters = "";
        StringTokenizer tokenizer = new StringTokenizer(deadCharacters, ",");

        while (tokenizer.hasMoreTokens()) {
            this.deadCharacters += tokenizer.nextToken();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUseSysProxy() {
        return useSysProxy;
    }

    public void setUseSysProxy(boolean useSysProxy) {
        this.useSysProxy = useSysProxy;
    }

    public boolean isLoadWeak() {
        return loadWeak;
    }

    public void setLoadWeak(boolean loadWeak) {
        this.loadWeak = loadWeak;
    }

    public boolean isLoadNonPrimary() {
        return loadNonPrimary;
    }

    public void setLoadNonPrimary(boolean loadNonPrimary) {
        this.loadNonPrimary = loadNonPrimary;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setFuzzySearch(boolean fuzzySearch) {
        this.fuzzySearch = fuzzySearch;
    }

}
