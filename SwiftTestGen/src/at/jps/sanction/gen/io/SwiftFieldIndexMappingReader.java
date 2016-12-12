package at.jps.sanction.gen.io;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwiftFieldIndexMappingReader {

    public static Map<String, String> readFieldIndexMapping(final String filename) {
        final Map<String, String> fieldIndexMappings = new HashMap<>();

        final List<String[]> lines = CSVFileReader.readCSVFile(filename, ",");

        for (final String line[] : lines) {
            fieldIndexMappings.put(line[0], line[1]);
        }

        return fieldIndexMappings;
    }

    public static void main(String[] args) {

    }

}
