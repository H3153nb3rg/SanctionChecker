package at.jps.sanction.model.listhandler;

import java.util.Properties;

public interface ReferenceListHandler {

    String getListName();

    Properties getValues();

    void initialize();
}
