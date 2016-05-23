package at.jps.sanction.core.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class TimeStamp {

    private static SimpleDateFormat sdf_human   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_machine = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static String getCurrentNanos() {
        return sdf_machine.format(Date.from(Instant.now()));
    }

    public static String getCurrentReadable() {
        return sdf_human.format(Date.from(Instant.now()));
    }

    public static String formatAsCompact(long nanos) {
        return sdf_machine.format(new Date(nanos));
    }

    public static String formatAsReadably(long nanos) {
        return sdf_human.format(new Date(nanos));
    }

}
