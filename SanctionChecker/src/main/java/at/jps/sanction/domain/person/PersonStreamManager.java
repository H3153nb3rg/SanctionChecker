package at.jps.sanction.domain.person;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.core.listhandler.valuelist.IAListHandler;
import at.jps.sanction.core.listhandler.valuelist.NSWHListHandler;
import at.jps.sanction.core.listhandler.valuelist.SWListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;

public class PersonStreamManager extends StreamManager {

    @Override
    public ValueListHandler getIndexAusschlussList() {
        return getValueListHandlers().get(IAListHandler.LISTNAME);  // TODO: this is nasty !!
    }

    @Override
    public ValueListHandler getNotSingleWordHitList() {
        return getValueListHandlers().get(NSWHListHandler.LISTNAME);// TODO: this is nasty !!
    }

    @Override
    public ValueListHandler getStopwordList() {
        return getValueListHandlers().get(SWListHandler.LISTNAME);// TODO: this is nasty !! all point to same !
    }

}
