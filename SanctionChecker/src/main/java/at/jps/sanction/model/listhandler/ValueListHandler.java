package at.jps.sanction.model.listhandler;

import java.util.Collection;

public interface ValueListHandler {

    String getListName();

    String getFilename();

    Collection<String> getValues();

    void initialize();
}
