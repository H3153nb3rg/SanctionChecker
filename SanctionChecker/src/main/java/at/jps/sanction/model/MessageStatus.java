package at.jps.sanction.model;

import com.avaje.ebean.annotation.EnumValue;

public enum MessageStatus {
    @EnumValue("D") DELETED,
    @EnumValue("H") HIT,
    @EnumValue("I") INCONSPICIOUS,
    @EnumValue("B") BLOCK,
    @EnumValue("R") RELEASE,
    @EnumValue("C") CHECK,
    @EnumValue("N") NEW,
    @EnumValue("1") BUSY_ANALYSE,
    @EnumValue("2") BUSY_MANUAL,
    @EnumValue("3") BUSY_POSTPROCESS
}
