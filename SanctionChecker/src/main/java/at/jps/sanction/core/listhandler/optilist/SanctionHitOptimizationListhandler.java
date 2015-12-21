package at.jps.sanction.core.listhandler.optilist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.OptimizationListHandler;

public abstract class SanctionHitOptimizationListhandler implements OptimizationListHandler {

    protected String                                 LISTNAME                       = "NoHitOptiList";
    static final Logger                              logger                         = LoggerFactory.getLogger(SanctionHitOptimizationListhandler.class);

    public static SanctionHitOptimizationListhandler instance;

    private String                                   filename;

    private List<OptimizationRecord>                 optiRecords;

    private boolean                                  autoDiscardHitsOnConfirmStatus = false;

    // protected String propertyToken = PropertyKeys.PROP_TXNOHITOPTILIST_DEF;

    public SanctionHitOptimizationListhandler() {

    }

    @Override
    public String getListName() {
        return LISTNAME;
    }

    @Override
    public List<OptimizationRecord> getValues() {

        if (optiRecords == null) {
            optiRecords = new ArrayList<OptimizationRecord>();
        }

        return optiRecords;
    }

    @Override
    public void initialize() {
        instance = this;

        // autoDiscardHitsOnConfirmStatus = Boolean.parseBoolean(properties.getProperty(propertyToken + ".autoDiscardHitsOnConfirmStatus", "false"));

        // filename = properties.getProperty(propertyToken + ".filename");
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

                        OptimizationRecord optiRecord = new OptimizationRecord();
                        StringTokenizer tokenizer = new StringTokenizer(line, ";");

                        optiRecord.setFieldName(tokenizer.nextToken());
                        optiRecord.setToken(tokenizer.nextToken());
                        optiRecord.setWatchListName(tokenizer.nextToken());
                        optiRecord.setWatchListId(tokenizer.nextToken());
                        optiRecord.setStatus(tokenizer.nextToken());

                        logger.debug(" Opti :" + optiRecord.toString());
                        if (!getValues().contains(optiRecord)) {
                            getValues().add(optiRecord);
                        }

                    }
                } while (line != null);

            }
            finally {
                input.close();
            }
        }
        catch (final Exception x) {
            logger.error("parsing failed reading " + LISTNAME + " file: " + filename + " Exception: " + x.toString());
            logger.debug("Exception : ", x);
        }

        logger.info("finished reading " + LISTNAME + " file:" + filename);
        logger.info("finished reading Records :" + (getValues() != null ? getValues().size() : 0));
    }

    @Override
    /**
     * forceUpdate = false : if record is already in masterlist status is raised ( if possible) forceUpdate = true : if record is already in masterlist status from new one is taken
     */
    public void appendList(List<OptimizationRecord> optiRecords, boolean forceUpdate) {

        List<OptimizationRecord> freshNew = new ArrayList<OptimizationRecord>();

        for (OptimizationRecord newOptiRecord : optiRecords) {
            boolean found = false;
            for (OptimizationRecord oldOptiRecord : getValues()) {
                if (newOptiRecord.getWatchListName().equals(oldOptiRecord.getWatchListName()) && newOptiRecord.getWatchListId().equals(oldOptiRecord.getWatchListId())
                        && newOptiRecord.getFieldName().equals(oldOptiRecord.getFieldName()) && newOptiRecord.getToken().equals(oldOptiRecord.getToken())) {
                    found = true;

                    if (forceUpdate) {
                        oldOptiRecord.setStatus(newOptiRecord.getStatus());
                    }
                    else {

                        // increase safely level if necessary

                        int i = 0;
                        for (String code : OptimizationRecord.statusCode) {
                            if (code.equals(oldOptiRecord.getStatus())) {
                                // found = true;
                                break;
                            }
                            else {
                                i++;
                            }
                        }
                        if (i < OptimizationRecord.statusCode.length - 1) {
                            i++;
                            oldOptiRecord.setStatus(OptimizationRecord.statusCode[i]);
                        }
                    }
                    break;
                }
            }
            if (!found) {
                freshNew.add(newOptiRecord);
            }

        }

        // add new
        getValues().addAll(freshNew);

    }

    public void removeList(List<OptimizationRecord> optiRecords) {

        List<OptimizationRecord> toBeRemoved = new ArrayList<OptimizationRecord>();

        for (OptimizationRecord newOptiRecord : optiRecords) {
            for (OptimizationRecord oldOptiRecord : getValues()) {
                if (newOptiRecord.getWatchListName().equals(oldOptiRecord.getWatchListName()) && newOptiRecord.getWatchListId().equals(oldOptiRecord.getWatchListId())
                        && newOptiRecord.getFieldName().equals(oldOptiRecord.getFieldName()) && newOptiRecord.getToken().equals(oldOptiRecord.getToken())) {

                    toBeRemoved.add(oldOptiRecord);
                    break;
                }
            }
        }

        // add new
        getValues().removeAll(toBeRemoved);

    }

    @Override
    public void writeList(List<OptimizationRecord> optiRecords, boolean append) {
        writeList(filename, optiRecords, append);
    }

    public void writeList(final String filename, List<OptimizationRecord> optiRecords, boolean append) {

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, append)));

            for (OptimizationRecord optiRecord : optiRecords) {
                out.println(optiRecord.toString());
            }

            out.close();
        }
        catch (IOException e) {
            // exception handling left as an exercise for the reader
        }

    }

    @Override
    public boolean isAutoDiscardHitsOnConfirmStatus() {
        return autoDiscardHitsOnConfirmStatus;
    }

    public void setAutoDiscardHitsOnConfirmStatus(boolean autoDiscardHitsOnConfirmStatus) {
        this.autoDiscardHitsOnConfirmStatus = autoDiscardHitsOnConfirmStatus;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
