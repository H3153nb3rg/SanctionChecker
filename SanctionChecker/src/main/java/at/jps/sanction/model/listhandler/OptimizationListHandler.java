package at.jps.sanction.model.listhandler;

import java.util.List;

import at.jps.sanction.model.OptimizationRecord;

public interface OptimizationListHandler {

    String getListName();

    List<OptimizationRecord> getValues();

    void initialize();

    void writeList(List<OptimizationRecord> optiRecords, boolean append);

    void appendList(List<OptimizationRecord> optiRecords, boolean forceUpdate);

    void removeList(List<OptimizationRecord> optiRecords);

    boolean isAutoDiscardHitsOnConfirmStatus();
}
