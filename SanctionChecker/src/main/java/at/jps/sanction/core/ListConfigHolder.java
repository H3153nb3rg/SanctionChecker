package at.jps.sanction.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import at.jps.sanction.core.util.string.LuceneIndex;
import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;

public class ListConfigHolder implements ApplicationContextAware {

    static final Logger                           logger = LoggerFactory.getLogger(ListConfigHolder.class);

    private static ApplicationContext             context;

    private HashMap<String, SanctionListHandler>  watchLists;
    private HashMap<String, ReferenceListHandler> referenceLists;
    private HashMap<String, ValueListHandler>     valueLists;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(final ApplicationContext ac) throws BeansException {
        context = ac;
    }

    public SanctionListHandler getWatchListByName(final String listname) {
        return watchLists.get(listname);
    }

    public HashMap<String, SanctionListHandler> getWatchLists() {
        return watchLists;
    }

    public HashMap<String, ReferenceListHandler> getReferenceLists() {
        return referenceLists;
    }

    public ReferenceListHandler getReferenceListByName(final String listname) {
        return referenceLists.get(listname);
    }

    public HashMap<String, ValueListHandler> getValueLists() {
        return valueLists;
    }

    public ValueListHandler getValueListByName(final String listname) {
        return valueLists.get(listname);
    }

    public void setReferenceLists(final List<ReferenceListHandler> referenceListHandlers) {
        referenceLists = new HashMap<>();

        for (final ReferenceListHandler rlh : referenceListHandlers) {
            referenceLists.put(rlh.getListName(), rlh);
        }
    }

    public void setWatchLists(final List<SanctionListHandler> sanctionListHandlers) {
        watchLists = new HashMap<>();

        for (final SanctionListHandler slh : sanctionListHandlers) {
            watchLists.put(slh.getListName(), slh);
        }
    }

    public void setValueLists(final List<ValueListHandler> valueListHandlers) {
        valueLists = new HashMap<>();

        for (final ValueListHandler vlh : valueListHandlers) {
            valueLists.put(vlh.getListName(), vlh);
        }
    }

    public void initialize() {

        assert (getValueLists() != null) && (!getValueLists().isEmpty()) : "ValueLists not configured";
        assert (getWatchLists() != null) && (!getWatchLists().isEmpty()) : "WatchLists not configured";
        assert (getReferenceLists() != null) && (!getReferenceLists().isEmpty()) : "ReferenceLists not configured";

        final CountDownLatch reflatch = new CountDownLatch(getValueLists().size() + getWatchLists().size() + getReferenceLists().size());

        for (final String listname : getValueLists().keySet()) {
            final ValueListHandler lh = getValueLists().get(listname);
            logger.info("Handle List... " + lh.getListName());

            new Thread(new Runnable() {

                @Override
                public void run() {
                    lh.initialize();

                    reflatch.countDown();
                    logger.info(lh.getListName() + " latched down... " + reflatch.getCount());
                }
            }).start();
        }

        for (final String listname : getReferenceLists().keySet()) {
            final ReferenceListHandler lh = getReferenceLists().get(listname);
            logger.info("Handle List... " + lh.getListName());

            new Thread(new Runnable() {

                @Override
                public void run() {
                    lh.initialize();

                    reflatch.countDown();
                    logger.info(lh.getListName() + " latched down... " + reflatch.getCount());
                }
            }).start();
        }

        for (final String listname : getWatchLists().keySet()) {
            final SanctionListHandler lh = getWatchLists().get(listname);
            logger.info("Handle List... " + lh.getListName());

            new Thread(new Runnable() {

                @Override
                public void run() {
                    lh.initialize();
                    lh.initDone();  // post initialization phase

                    reflatch.countDown();
                    logger.info(lh.getListName() + " latched down... " + reflatch.getCount());
                }
            }).start();
        }

        try {
            reflatch.await();

            final HashMap<String, Directory> lucyDirs = LuceneIndex.getSearchIndices();

            for (final String key : lucyDirs.keySet()) {

                final Directory dir = lucyDirs.get(key);
                try {
                    for (final String entry : dir.listAll()) {
                        System.out.println("LUCY: " + entry);
                    }
                }
                catch (final IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        catch (final InterruptedException e) {
            logger.error("Loading Lists background job sync failed!!");
            logger.debug("Exception", e);
        }

        logger.info("Loading Lists done");
        logger.info("Lists Configuration finished successfully!!");
        logger.info("-------------------------------------------");
    }
}
