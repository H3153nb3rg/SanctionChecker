package at.jps.sanction.gen.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwiftMTDefReader {

    public static Map<String, List<String>> readMTsFromFile(final String filename) {

        final Map<String, List<String>> messageTypes = new HashMap<>();

        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(filename));

            String line = null;

            do {

                line = br.readLine();
                if ((line != null) && (line.trim().length() > 0) && (!line.trim().startsWith("*%")) && (line.substring(0, 8).trim().length() > 0)) {

                    final String fields[] = line.substring(0, 8).trim().replace("\t", " ").split(" ");
                    if (fields.length > 1) {
                        // System.out.println(fields[0] + " : " + fields[1] + " --> " + line);

                        final String mt = fields[0];
                        final String field = fields[1];

                        if (!messageTypes.containsKey(mt)) {
                            messageTypes.put(mt, new ArrayList<String>());
                        }

                        final List<String> fieldList = messageTypes.get(mt);

                        fieldList.add(field);

                    }
                    else {
                        // System.out.println(" !!!!!!!!!!!!!! line conains garbage--> " + line);
                    }

                }
            } while (line != null);

        }
        catch (final Exception x) {
            x.printStackTrace();
        }
        finally {
            try {
                br.close();
            }
            catch (final IOException e) {
            }
        }

        return messageTypes;
    }

    static void printMTs(Map<String, List<String>> mts) {

        for (final String mt : mts.keySet()) {
            final StringBuilder sb = new StringBuilder(1024);

            sb.append(mt + ": ");

            for (final String field : mts.get(mt)) {
                sb.append(field + ", ");
            }
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {

        // mt -> fields
        // field -> index
        // index -> watchlists
        // watchlist -> testentries

        final String mTDefFilename = "C:\\Users\\johannes\\Sanctionlists\\mttypes.txt";
        printMTs(readMTsFromFile(mTDefFilename));
    }

}
