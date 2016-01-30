package at.jps.sanction.model;

import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sanction.model.sl.entities.WL_Entity;

public interface WatchListInformant {

    public WL_Entity getSanctionListEntityDetails(final String listname, final String id);

    public ReferenceListHandler getReferenceListByName(final String name);

    public SanctionListHandler getWatchListByName(final String listname);

    public ValueListHandler getValueListByName(final String name);

    public String getSanctionListDescription(final String listname);

}
