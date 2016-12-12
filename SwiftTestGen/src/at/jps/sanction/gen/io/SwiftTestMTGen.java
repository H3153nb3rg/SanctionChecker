package at.jps.sanction.gen.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class SwiftTestMTGen {

    private static void writeFile(String filename, String content) {
        try {

            final File file = new File(filename);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            final FileWriter fw = new FileWriter(file.getAbsoluteFile());
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        }
        catch (final IOException e) {
            e.printStackTrace();
        }

    }

    private static String generateHeader(final String mt, boolean sender) {
        String header = "{1:F01BKAUATW0AEEM0000123456}";

        if (sender) {
            header += "{2:" + (sender ? "I" : "O") + mt + "AFRPZAJ1XXXN3003}";
        }
        else {
            final SimpleDateFormat sdf = new SimpleDateFormat("kkmmyyMMdd");
            final SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddkkmm");
            final Calendar cal = Calendar.getInstance();

            header += "{2:" + (sender ? "I" : "O") + mt + sdf.format(cal.getTime()) + "BKAUATW0AEEM0000123456" + sdf1.format(cal.getTime()) + "U}";
        }
        header += "{3:{113:SEPA}{108:ILOVESEPA}}{4:";

        return header;
    }

    static String getTestData(Map<String, List<String>> wlTestData, final String wachtlist) {
        final List<String> testdatas = wlTestData.get(wachtlist);

        final Random rand = new Random();

        final String testdata = testdatas.get(rand.nextInt(testdatas.size()));

        return testdata;
    }

    public static void generateTestData(Map<String, List<String>> mtdefinition, boolean sender, Map<String, String> fieldIndexMapping, Map<String, List<String>> indexDefinitons, Map<String, List<String>> wlTestData, int ix, final String OutputDir) {

        final Random rand = new Random();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddkkmmssS");
        final Calendar cal = Calendar.getInstance();

        for (final String mt : mtdefinition.keySet()) {
            final StringBuffer sb = new StringBuffer();

            // generate other sections
            sb.append(generateHeader(mt, sender)).append(System.lineSeparator());

            // copyright
            sb.append(":XX:Dr Jones TX " + (sender ? "I" : "O") + mt).append("\r");
            sb.append(":20:DRJ" + mt + (sender ? "I" : "O") + sdf.format(cal.getTime())).append("\r");

            for (final String field : mtdefinition.get(mt)) {
                sb.append(":" + field + ":");

                // add qualifier (if)

                // :TODO <------------------

                // add testdata
                // iterate over all watchlists for the index of this field and collect some random test data

                final String index = fieldIndexMapping.get(field);
                if (index != null) {
                    final List<String> watchlists = indexDefinitons.get(index);
                    if (watchlists != null) {

                        // just take one index value not more
                        final String watchlist = watchlists.get(rand.nextInt(watchlists.size()));

                        // for (final String watchlist : watchlists.) {
                        final String testdata = getTestData(wlTestData, watchlist);  // generates ALL

                        sb.append(testdata + " ");
                        // }
                    }
                }
                sb.append("\r");
            }

            // post sections
            sb.append("-}"); // .append("\r"); // append(System.lineSeparator()); // <--- IS THIS A BUG OR FEATURE ?

            final String filename = mt + (sender ? "I" : "O") + "-" + String.format("%03d", ix);

            writeFile(OutputDir + "/mt" + filename + ".txt", sb.toString());

            System.out.println("generated :" + filename);
        }
    }

    static Properties readProperties(final String filename) {
        final Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(filename);

            // load a properties file
            prop.load(input);

        }
        catch (final IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Please provide property file as parameter: ");
        }
        else {

            final Properties properties = readProperties(args[0]);

            if (properties.isEmpty()) {
                System.err.println("Please provide valid property file as parameter:" + args[0] + " seems not to be valid or does not contain enough information...");
            }
            else {
                // mt -> fields
                // field -> index
                // index -> watchlists
                // watchlist -> testentries

                final String mTDefFilename = properties.getProperty("MTDefinitionFile");// "C:\\Users\\johannes\\Sanctionlists\\mttypes.txt";
                final String fieldIndexMappingFilename = properties.getProperty("FieldIndexMappingFile"); // "C:\\Users\\johannes\\Sanctionlists\\fieldmapping.csv";
                final String indexDefinitionFilename = properties.getProperty("IndexDefinitionFile"); // "C:\\Users\\johannes\\Sanctionlists\\indexDefinition.csv";
                final String wlTestDataFilename = properties.getProperty("TestDataFile"); // "C:\\Users\\johannes\\Sanctionlists\\wlTestData.csv";
                final String outDir = properties.getProperty("OutputDir");
                int nrOfMsgs = Math.abs(Integer.parseInt(properties.getProperty("NrOfMsgToGenerate", "1")));

                if (nrOfMsgs > 999) {
                    nrOfMsgs = 999;
                }

                final File outDirectory = new File(outDir);
                outDirectory.mkdirs();

                final Map<String, String> fieldIndexMappings = SwiftFieldIndexMappingReader.readFieldIndexMapping(fieldIndexMappingFilename);
                final Map<String, List<String>> indexDefinitons = SwiftIndexDefinitionReader.readIndexDefinition(indexDefinitionFilename);
                final Map<String, List<String>> wlTestData = SwiftTestDataReader.readWLTestData(wlTestDataFilename);

                for (int i = 0; i < nrOfMsgs; i++) {
                    generateTestData(SwiftMTDefReader.readMTsFromFile(mTDefFilename), (i % 2) == 0, fieldIndexMappings, indexDefinitons, wlTestData, i, outDir);
                }
            }
        }
    }

}
