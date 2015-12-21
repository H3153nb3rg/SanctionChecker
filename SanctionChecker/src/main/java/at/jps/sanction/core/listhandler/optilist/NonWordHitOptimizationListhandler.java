package at.jps.sanction.core.listhandler.optilist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.listhandler.NoWordHitListHandler;

public class NonWordHitOptimizationListhandler implements NoWordHitListHandler {

    public final static String                      LISTNAME = "nowordhitlist";
    static final Logger                             logger   = LoggerFactory.getLogger(NonWordHitOptimizationListhandler.class);
    public static NonWordHitOptimizationListhandler instance;

    private String                                  filename;

    // private List<OptimizationRecord> optiRecords;

    private MultiValuedMap<String, String>          mvm;

    public NonWordHitOptimizationListhandler() {

    }

    @Override
    public String getListName() {
        return LISTNAME;
    }

    @Override
    public MultiValuedMap<String, String> getValues() {

        if (mvm == null) {
            mvm = new ArrayListValuedHashMap<String, String>();
        }

        return mvm;
    }

    @Override
    public void initialize() {
        instance = this;

        // filename = properties.getProperty(PropertyKeys.PROP_NOWORDHITLIST_DEF + ".filename");
        readList(getFilename());
    }

    private void readList(final String filename) {
        logger.info("start reading " + LISTNAME + " file:" + filename);

        try {
            final BufferedReader input = new BufferedReader(new FileReader(filename));
            try {
                String line = null;
                do {
                    line = input.readLine();
                    if (line != null) {

                        StringTokenizer tokenizer = new StringTokenizer(line, ";");

                        String key = tokenizer.nextToken();
                        String value = tokenizer.nextToken();

                        getValues().put(key, value);

                        logger.debug("nowordhitlist :" + key + "," + value);

                    }
                } while (line != null);

            }
            finally {
                input.close();
            }
        }
        catch (final Exception x) {
            logger.error("parsing failed reading " + LISTNAME + " file:" + filename + " Exception: " + x.toString());
            logger.debug("Exception : ", x);
        }

        logger.info("finished reading " + LISTNAME + " file:" + filename);
        logger.info("finished reading Records :" + (getValues() != null ? getValues().size() : 0));
    }

    @Override
    public void writeList(MultiValuedMap<String, String> noHitValues, boolean append) {
        writeList(filename, noHitValues, append);
    }

    public void writeList(final String filename, MultiValuedMap<String, String> noHitValues, boolean append) {

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));

            for (String key : noHitValues.keySet()) {
                for (String value : noHitValues.get(key)) {
                    out.println(key + ";" + value);
                }
            }

            out.close();
        }
        catch (IOException e) {
            // exception handling left as an exercise for the reader
        }

    }

    @Override
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
