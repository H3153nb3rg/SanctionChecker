package at.jps.sl.gui.util;

import java.util.Properties;

import at.jps.sanction.core.queue.persisted.FileOutputQueue;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.PropertyKeys;
import at.jps.sanction.model.queue.Queue;
import at.jps.sl.gui.AdapterHelper;

public class PersistentQueueAdapterBuilder {

    // input
    public static Queue<AnalysisResult> getHitQueue(final Properties properties) {

        return getQueueInternal(PropertyKeys.PROP_QUEUE_NAME_HIT, properties);
    }

    public static Queue<AnalysisResult> getNoHitQueue(final Properties properties) {

        return getQueueInternal(PropertyKeys.PROP_QUEUE_NAME_NOHIT, properties);
    }

    // analysis output
    public static Queue<AnalysisResult> getPPNoHitQueue(final Properties properties) {

        return getQueueInternal(PropertyKeys.PROP_QUEUE_NAME_PP_NOHIT, properties);
    }

    public static Queue<AnalysisResult> getPPHitQueue(final Properties properties) {

        return getQueueInternal(PropertyKeys.PROP_QUEUE_NAME_PP_HIT, properties);
    }

    // output
    public static Queue<AnalysisResult> getFinalNoHitQueue(final Properties properties) {

        return getQueueInternal(PropertyKeys.PROP_QUEUE_NAME_FINAL_NOHIT, properties);
    }

    public static Queue<AnalysisResult> getFinalHitQueue(final Properties properties) {

        return getQueueInternal(PropertyKeys.PROP_QUEUE_NAME_FINAL_HIT, properties);
    }

    public static Queue<AnalysisResult> getBacklogQueue(final Properties properties) {

        return getQueueInternal(PropertyKeys.PROP_QUEUE_NAME_BACKLOG, properties);
    }

    private static Queue<AnalysisResult> getQueueInternal(final String queueName, final Properties properties) {
        Queue<AnalysisResult> queue = null;

        final String streamName = properties.getProperty(AdapterHelper.PROP_GUI_MAIN, "");

        // TODO: read definition & implementation from props !!!

        final String qname = properties.getProperty("GUI." + streamName + ".QueueBase." + queueName, "");

        queue = new FileOutputQueue(queueName, 1234); // TODO: nasty !!
        // !!!!!
        queue.setBasePath(qname);

        return queue;

    }

}
