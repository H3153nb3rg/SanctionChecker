package at.jps.sanction.model.listhandler;

import java.util.List;

public interface ValueListHandler {

    String getListName();

    String getFilename();

    List<String> getValues();

    void initialize();
}
