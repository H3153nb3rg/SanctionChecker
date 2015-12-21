package at.jps.sanction.core.util;

import at.jps.sanction.core.listhandler.reflist.BicListHandler;

public class BICHelper {

    public static String extendBIC(final String text) {

        String temp = text;
        while (temp.length() < 11) {
            temp = temp + "X";
        }

        final String longText = (String) BicListHandler.getInstance().getValues().get(temp);

        return longText;
    }

}
