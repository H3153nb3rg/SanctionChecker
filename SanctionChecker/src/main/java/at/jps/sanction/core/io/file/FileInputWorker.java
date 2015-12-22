/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.io.file;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.io.file.FileParser;
import at.jps.sanction.model.worker.in.InputWorker;

public class FileInputWorker extends InputWorker {

    static final Logger logger = LoggerFactory.getLogger(FileInputWorker.class);

    // Properties properties;
    private FileParser  fileParser;

    private String      path;
    private String      pattern;

    public FileInputWorker() {
        super();
    }

    FileInputWorker(final StreamManager manager) {
        super(manager);
    }

    @Override
    public void checkForMessages() {

        try {
            final File[] files = getFileList();
            if (files.length > 0) {
                parseFiles(files);
            }
        }
        catch (final Exception x) {
            logger.error("error while waiting for files " + x.toString());
            logger.debug("Exception: ", x);
        }
    }

    public void initialize() {
        // NOOP
    }

    public void close() {
        // rename back if failed !!
    }

    private synchronized File[] getFileList() {

        // final File directory = new File(getStreamManager().getProperties().getProperty(getStreamManager().getStreamName() + "." + PropertyKeys.PROP_INPUT_FOLDER, "C:\\temp"));

        final File directory = new File(getPath());

        // final String pattern = getStreamManager().getProperties().getProperty(getStreamManager().getStreamName() + "." + PropertyKeys.PROP_INPUT_FILEPATTERN, "*.*");

        final FileFilter fileFilter = new WildcardFileFilter(getPattern());
        final File[] files = directory.listFiles(fileFilter);

        if (files != null) {
            logger.info(files.length + " files found in: " + directory.getAbsolutePath());
        }
        else {
            logger.info("No files found in: " + directory.getAbsolutePath());
        }

        return files;

    }

    @Override
    public Message getNewMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    FileParser getFileParser() {
        // if (parser == null) {
        // parser = BaseFactory.createFileParser(getStreamManager());
        // }
        return fileParser;
    }

    protected void parseFiles(final File[] files) {
        for (final File file : files) {

            if (file.exists()) {
                logger.info("start parsing file: " + file);
                // TODO: atomic & recoverable !!!!

                final File bsyFile = new File(file.getAbsolutePath() + ".bsy");
                file.renameTo(bsyFile);

                final boolean parsedOK = getFileParser() != null ? getFileParser().parse(bsyFile) : false;

                final File endFile = new File(file.getAbsolutePath() + (parsedOK ? ".end" : ".err"));
                bsyFile.renameTo(endFile);

                if (parsedOK) {
                    logger.info("finished parsing file: " + file + " OK");
                }
                else {
                    logger.error("finished parsing file: " + file + " FAILED");
                }

            }
            else {
                logger.info("already taken file: " + file);
            }
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setFileParser(FileParser parser) {
        this.fileParser = parser;
    }

    public void setStreamManager(final StreamManager manager) {
        super.setStreamManager(manager);
        // fileParser.setStreamManager(manager);
    }

}
