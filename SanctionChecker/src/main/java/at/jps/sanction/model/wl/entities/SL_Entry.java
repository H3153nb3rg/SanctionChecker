package at.jps.sanction.model.wl.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import at.jps.sanction.model.BaseModel;

public class SL_Entry extends BaseModel implements Serializable {

    /**
     *
     */
    private static final long                       serialVersionUID = 1797664991619104227L;

    WL_Entity                                       referencedEntity;

    private String                                  searchValue;

    transient private HashMap<String, List<String>> tokenizedString;

    public WL_Entity getReferencedEntity() {
        return referencedEntity;
    }

    public void setReferencedEntity(WL_Entity referencedEntity) {
        this.referencedEntity = referencedEntity;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public List<String> getTokenizedString(final String listName) {
        return getTokenizedStringMap().get(listName);
    }

    public void setTokenizedString(final String listName, List<String> tokenizedString) {
        getTokenizedStringMap().put(listName, tokenizedString);
    }

    private HashMap<String, List<String>> getTokenizedStringMap() {
        if (tokenizedString == null) {
            tokenizedString = new HashMap<>();
        }
        return tokenizedString;
    }

}
