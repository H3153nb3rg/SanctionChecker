/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.io.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.queue.Queue;
import at.jps.sanction.model.worker.out.OutputWorker;

public class FileOutputWorker extends OutputWorker {

    static final Logger logger = LoggerFactory.getLogger(FileOutputWorker.class);

    Properties          properties;
    BufferedWriter      writer;

    private String      filename;
    private String      path;

    public FileOutputWorker() {
        super();
    }

    FileOutputWorker(final StreamManager manager, final Queue<AnalysisResult> queue) {
        super(manager, queue);
        initialize();
    }

    String getUniqueFilename() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd-hhmmss.SSS");

        final String name = getQueue().getName() + "-" + sdf.format(new Date()) + ".txt";

        return name;

    }

    public void initialize() {
    }

    public void close() {
        if (writer != null) {
            try {
                writer.close();
            }
            catch (IOException e) {
                logger.error("Error closing:" + filename);
                logger.debug("Exception: ", e);
            }
        }
    }

    @Override
    public void handleMessage(final AnalysisResult message) {
        try {

            logger.info("write Message: " + message.getMessage().getId());

            getWriter().write(message.getMessage().toString());

            if (message.getHitList() != null) {
                for (final HitResult hit : message.getHitList()) {

                    getWriter().write(hit.toString());
                    getWriter().newLine();
                    getWriter().write("----------------------------");
                    getWriter().newLine();

                }
            }
            else {
                final String exception = message.getException();
                if (exception != null) {
                    getWriter().write("ERROR: ");
                    getWriter().write(exception);
                    getWriter().newLine();
                    getWriter().write("----------------------------");
                    getWriter().newLine();
                }
            }
            getWriter().write("============================");
            getWriter().newLine();

            getWriter().newLine();
            getWriter().flush();
        }
        catch (final IOException e) {
            logger.error("Error writing to file:" + filename);
            logger.debug("Exception: ", e);
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BufferedWriter getWriter() {

        if (writer == null) {
            try {

                final String filename = (getFilename() != null ? getFilename() : (getPath() + File.separator + getUniqueFilename()));

                // final String filename = getStreamManager().getProperties().getProperty(getStreamManager().getStreamName() + "." + PropertyKeys.PROP_OUTPUT_FOLDER, "C:\\temp") + File.separator
                // + getUniqueFilename();

                writer = new BufferedWriter(new FileWriter(filename));

            }
            catch (final IOException e) {
                logger.error(e.toString());
            }
        }
        return writer;
    }

}
