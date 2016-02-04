package at.jps.sanction.domain;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.core.listhandler.valuelist.IAListHandler;
import at.jps.sanction.core.listhandler.valuelist.NSWHListHandler;
import at.jps.sanction.core.listhandler.valuelist.SWListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;

public class SanctionStreamManager extends StreamManager {

    @Override
    public ValueListHandler getIndexAusschlussList() {
        return getValueListHandlers().get(IAListHandler.LISTNAME);  // TODO: This should be moved to propertyfile
    }

    @Override
    public ValueListHandler getNotSingleWordHitList() {
        return getValueListHandlers().get(NSWHListHandler.LISTNAME);  // TODO: This should be moved to propertyfile
    }

    @Override
    public ValueListHandler getStopwordList() {
        return getValueListHandlers().get(SWListHandler.LISTNAME);  // TODO: This should be moved to propertyfile
    }

    public boolean isFieldIBAN(final String fieldname) {
        return false;
    }
}
