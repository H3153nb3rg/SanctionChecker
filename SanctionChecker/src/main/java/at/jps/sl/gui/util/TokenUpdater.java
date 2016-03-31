package at.jps.sl.gui.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TokenUpdater {

    public static void addStopWord(final String token, final String filename) {

        // TODO: no hardcoded dependecy here
        appendToFile(token, filename);
    }

    public static void addStopNSWH(final String token, final String filename) {

        // TODO: no hardcoded dependecy here
        appendToFile(token, filename);
    }

    public static void addStopIA(final String token, final String filename) {

        // TODO: no hardcoded dependecy here
        appendToFile(token, filename);
    }

    public static void addNoHitInfo(final String fieldToken, final String listToken, final String filename) {

        // TODO: no hardcoded dependecy here
        appendToFile(fieldToken + ";" + listToken, filename);
    }

    private static void appendToFile(final String token, final String filename) {
        try {
            final PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            out.println(token);
            out.close();
        }
        catch (final IOException e) {
            // exception handling left as an exercise for the reader
        }
    }
}
