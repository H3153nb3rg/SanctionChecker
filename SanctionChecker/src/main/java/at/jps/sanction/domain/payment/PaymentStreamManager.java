package at.jps.sanction.domain.payment;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.core.listhandler.valuelist.IAListHandler;
import at.jps.sanction.core.listhandler.valuelist.NSWHListHandler;
import at.jps.sanction.core.listhandler.valuelist.SWListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;

public class PaymentStreamManager extends StreamManager {

    @Override
    public ValueListHandler getIndexAusschlussList() {
        return getValueListHandlers().get(IAListHandler.LISTNAME);
    }

    @Override
    public ValueListHandler getNotSingleWordHitList() {
        return getValueListHandlers().get(NSWHListHandler.LISTNAME);
    }

    @Override
    public ValueListHandler getStopwordList() {
        return getValueListHandlers().get(SWListHandler.LISTNAME);
    }

    public boolean isFieldIBAN(final String fieldname) {
        return false;
    }
}
