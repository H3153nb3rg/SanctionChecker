package at.jps.sanction.gen.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwiftTestDataReader {

    public static Map<String, List<String>> readWLTestData(final String filename) {
        final Map<String, List<String>> wlTestDataMappings = new HashMap<>();

        final List<String[]> lines = CSVFileReader.readCSVFile(filename, ",");

        for (final String watchlist : lines.get(0)) {
            wlTestDataMappings.put(watchlist, new ArrayList<String>());
        }

        final String watchlists[] = lines.get(0);

        int linenr = 0;
        for (final String line[] : lines) {

            if (linenr > 0) {
                for (int i = 0; i < line.length; i++) {
                    if ((line[i] != null) && (line[i].trim().length() > 0)) {
                        final List<String> testdataList = wlTestDataMappings.get(watchlists[i]);
                        testdataList.add(line[i]);
                    }
                }
            }
            linenr++;
        }

        return wlTestDataMappings;
    }

    public static void main(String[] args) {

    }

}
