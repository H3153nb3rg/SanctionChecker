package at.jps.sl.gui.util;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLHelper {

    public static void openWebpage(final URI uri) {
        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if ((desktop != null) && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(final URL url) {
        try {
            openWebpage(url.toURI());
        }
        catch (final URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
