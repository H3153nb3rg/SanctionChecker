package at.jps.sanction.core.listhandler.tool;

import java.util.HashMap;
import java.util.StringTokenizer;

import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sanction.model.wl.entities.WL_Name;

public class WordWeightAnalyser {

    HashMap<String, HashMap<String, WordWeight>> wordweightLists;

    public HashMap<String, WordWeight> getWordWeightList(String listName) {
        HashMap<String, WordWeight> wwl = getWordweightLists().get(listName);
        if (wwl == null) {
            wwl = new HashMap<>();
            getWordweightLists().put(listName, wwl);
        }
        return wwl;
    }

    public HashMap<String, HashMap<String, WordWeight>> getWordweightLists() {

        if (wordweightLists == null) {
            setWordweightLists(new HashMap<String, HashMap<String, WordWeight>>());
        }

        return wordweightLists;
    }

    public void setWordweightLists(HashMap<String, HashMap<String, WordWeight>> wordweightLists) {
        this.wordweightLists = wordweightLists;
    }

    public void addEntity(String listName, WL_Entity wl_Entity) {

        for (final WL_Name wl_Names : wl_Entity.getNames()) {
            final String name = wl_Names.getWholeName();

            final StringTokenizer textTokenizer = new StringTokenizer(name, " ;-/()");

            while (textTokenizer.hasMoreElements()) {
                final String token = textTokenizer.nextToken();

                WordWeight ww = getWordWeightList(listName).get(token);

                if (ww == null) {
                    ww = new WordWeight(token);
                    getWordWeightList(listName).put(token, new WordWeight(token));
                }
                ww.setCounter(ww.getCounter() + 1);

            }

        }

    }

}
