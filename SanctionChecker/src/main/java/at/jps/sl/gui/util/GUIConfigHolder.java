package at.jps.sl.gui.util;

import java.awt.Color;
import java.util.HashMap;

import at.jps.sanction.core.StreamConfig;
import at.jps.sanction.domain.payment.PaymentListConfigHolder;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.queue.Queue;

public class GUIConfigHolder extends PaymentListConfigHolder {

    final public static String            QUEUE_NAME_HITS       = "hitQueue";
    final public static String            QUEUE_NAME_NOHITS     = "noHitQueue";
    final public static String            QUEUE_NAME_POSTHIT    = "postProcessHitQueue";
    final public static String            QUEUE_NAME_POSTNOHIT  = "postProcessNoHitQueue";
    final public static String            QUEUE_NAME_FINALHIT   = "finalHitQueue";
    final public static String            QUEUE_NAME_FINALNOHIT = "finalNoHitQueue";
    final public static String            QUEUE_NAME_BACKLOG    = "backlogQueue";

    private HashMap<String, StreamConfig> streamConfigs;

    private HashMap<String, Color>        fieldColors;
    private HashMap<String, String>       fieldColorsHex;
    private HashMap<String, String>       fieldNames;

    private String                        name;

    private String                        activeStream;

    private boolean                       autolearnMode         = true;

    @Override
    public void initialize() {
        super.initialize();

        assert (getStreamConfigs() != null) && (!getStreamConfigs().isEmpty()) : "StreamConigs not configured";

        activeStream = getStreamConfigs().keySet().iterator().next();  // default to first stream
    }

    public HashMap<String, String> getFieldColorsAsHex() {
        return fieldColorsHex;
    }

    public HashMap<String, Color> getFieldColors() {
        return fieldColors;
    }

    public void setFieldColors(final HashMap<String, String> fieldColorsText) {
        fieldColors = new HashMap<String, Color>();

        fieldColorsHex = fieldColorsText;

        for (final String key : fieldColorsText.keySet()) {
            fieldColors.put(key, Color.decode(fieldColorsText.get(key)));
        }

    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isAutolearnMode() {
        return autolearnMode;
    }

    public void setAutolearnMode(final boolean autolearnMode) {
        this.autolearnMode = autolearnMode;
    }

    public HashMap<String, String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(final HashMap<String, String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public HashMap<String, StreamConfig> getStreamConfigs() {
        return streamConfigs;
    }

    public void setStreamConfigs(final HashMap<String, StreamConfig> streamConfigs) {
        this.streamConfigs = streamConfigs;
    }

    public HashMap<String, String> getFieldColorsHex() {
        return fieldColorsHex;
    }

    public void setFieldColorsHex(final HashMap<String, String> fieldColorsHex) {
        this.fieldColorsHex = fieldColorsHex;
    }

    public String getActiveStream() {
        return activeStream;
    }

    public void setActiveStream(final String activeStream) {
        this.activeStream = activeStream;
    }

    @SuppressWarnings("unchecked")
    public Queue<AnalysisResult> getQueue(final String queueName) {
        return (Queue<AnalysisResult>) getStreamConfigs().get(getActiveStream()).getQueues().get(queueName);
    }

    public StreamConfig getStreamConfig(final String streamName) {
        return getStreamConfigs().get(streamName);
    }
}
