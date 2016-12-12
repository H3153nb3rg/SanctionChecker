package at.jps.sanction.gen.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwiftIndexDefinitionReader {

    public static Map<String, List<String>> readIndexDefinition(final String filename) {

        final Map<String, List<String>> fieldIndexMappings = new HashMap<>();

        final List<String[]> lines = CSVFileReader.readCSVFile(filename, ",");

        // line 1 contains watchlists
        final String watchlistnames[] = lines.get(0);

        int linenr = 0;
        for (final String line[] : lines) {

            if (linenr > 0) {
                fieldIndexMappings.put(line[0], new ArrayList<String>()); // indexname

                final List<String> watchlists = fieldIndexMappings.get(line[0]);

                for (int ix = 1; ix < line.length; ix++) {
                    if (line[ix].toUpperCase().trim().equals("X")) {
                        watchlists.add(watchlistnames[ix]);
                    }
                }
            }
            linenr++;
        }

        return fieldIndexMappings;
    }

    public static void main(String[] args) {

    }

}
