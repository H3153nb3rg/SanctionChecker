package at.jps.sl.gui.util;

import java.awt.Color;
import java.util.HashMap;

import at.jps.sanction.core.ListConfigHolder;
import at.jps.sanction.core.StreamConfig;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sanction.model.listhandler.OptimizationListHandler;
import at.jps.sanction.model.queue.Queue;

public class GUIConfigHolder extends ListConfigHolder {

    private StreamConfig            streamConfig;

    private OptimizationListHandler txNoHitOptimizationListHandler;
    private OptimizationListHandler txHitOptimizationListHandler;
    private NoWordHitListHandler    noWordHitListHandler;

    private Queue<AnalysisResult>   hitQueue;
    private Queue<AnalysisResult>   noHitQueue;
    private Queue<AnalysisResult>   postProcessHitQueue;
    private Queue<AnalysisResult>   postProcessNoHitQueue;
    private Queue<AnalysisResult>   finalHitQueue;
    private Queue<AnalysisResult>   finalNoHitQueue;
    private Queue<AnalysisResult>   backlogQueue;

    private HashMap<String, Color>  fieldColors;

    private String                  name;

    private boolean                 autolearnMode = true;

    public OptimizationListHandler getTxNoHitOptimizationListHandler() {
        return txNoHitOptimizationListHandler;
    }

    public void setTxNoHitOptimizationListHandler(OptimizationListHandler optimizationListHandler) {
        this.txNoHitOptimizationListHandler = optimizationListHandler;
    }

    public OptimizationListHandler getTxHitOptimizationListHandler() {
        return txHitOptimizationListHandler;
    }

    public void setTxHitOptimizationListHandler(OptimizationListHandler optimizationListHandler) {
        this.txHitOptimizationListHandler = optimizationListHandler;
    }

    public NoWordHitListHandler getNoWordHitListHandler() {
        return noWordHitListHandler;
    }

    public void setNoWordHitListHandler(NoWordHitListHandler noWordHitListHandler) {
        this.noWordHitListHandler = noWordHitListHandler;
    }

    public StreamConfig getStreamConfig() {
        return streamConfig;
    }

    public void setStreamConfig(StreamConfig streamConfig) {
        this.streamConfig = streamConfig;
    }

    public Queue<AnalysisResult> getPostProcessHitQueue() {
        return postProcessHitQueue;
    }

    public void setPostProcessHitQueue(Queue<AnalysisResult> postProcessHitQueue) {
        this.postProcessHitQueue = postProcessHitQueue;
    }

    public Queue<AnalysisResult> getPostProcessNoHitQueue() {
        return postProcessNoHitQueue;
    }

    public void setPostProcessNoHitQueue(Queue<AnalysisResult> postProcessNoHitQueue) {
        this.postProcessNoHitQueue = postProcessNoHitQueue;
    }

    public Queue<AnalysisResult> getFinalHitQueue() {
        return finalHitQueue;
    }

    public void setFinalHitQueue(Queue<AnalysisResult> finalHitQueue) {
        this.finalHitQueue = finalHitQueue;
    }

    public Queue<AnalysisResult> getFinalNoHitQueue() {
        return finalNoHitQueue;
    }

    public void setFinalNoHitQueue(Queue<AnalysisResult> finalNoHitQueue) {
        this.finalNoHitQueue = finalNoHitQueue;
    }

    public Queue<AnalysisResult> getBacklogQueue() {
        return backlogQueue;
    }

    public void setBacklogQueue(Queue<AnalysisResult> backlogQueue) {
        this.backlogQueue = backlogQueue;
    }

    public Queue<AnalysisResult> getNoHitQueue() {
        return noHitQueue;
    }

    public void setNoHitQueue(Queue<AnalysisResult> noHitQueue) {
        this.noHitQueue = noHitQueue;
    }

    public Queue<AnalysisResult> getHitQueue() {
        return hitQueue;
    }

    public void setHitQueue(Queue<AnalysisResult> hitQueue) {
        this.hitQueue = hitQueue;
    }

    public HashMap<String, Color> getFieldColors() {
        return fieldColors;
    }

    public void setFieldColors(HashMap<String, String> fieldColorsText) {
        fieldColors = new HashMap<String, Color>();

        for (String key : fieldColorsText.keySet()) {
            fieldColors.put(key, Color.decode(fieldColorsText.get(key)));
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAutolearnMode() {
        return autolearnMode;
    }

    public void setAutolearnMode(boolean autolearnMode) {
        this.autolearnMode = autolearnMode;
    }

}
