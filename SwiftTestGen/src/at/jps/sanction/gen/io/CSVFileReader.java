package at.jps.sanction.gen.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {

    // static final Logger logger = LoggerFactory.getLogger(CSVFileReader.class);

    public static List<String[]> readCSVFile(final String filename, final String separator) {
        final List<String[]> content = new ArrayList<>();

        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                final String[] tokenline = line.split(separator, -1);

                content.add(tokenline);
            }

        }
        catch (final Exception e) {

            System.err.println("error " + e.getMessage() + ": while parsing CSV file: " + filename);

            // logger.debug("error while parsing CSV file: " + filename);
            // logger.error("error while csv parsing: ", e);
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content;

    }

}
