package at.jps.sanction.model.listhandler;

import org.apache.commons.collections4.MultiValuedMap;

public interface NoWordHitListHandler {

    String getListName();

    public String getFilename();

    MultiValuedMap<String, String> getValues();

    void initialize();

    void writeList(MultiValuedMap<String, String> noHitValues, boolean append);

    // void appendList(MultiValueMap<String, String> noHitValues, boolean forceUpdate);
}
