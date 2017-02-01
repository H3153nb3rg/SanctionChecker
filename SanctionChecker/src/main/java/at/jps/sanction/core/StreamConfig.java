package at.jps.sanction.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import at.jps.sanction.model.queue.Queue;

public interface StreamConfig {

    // int getMinRelVal(final String fieldname, boolean reverseContains);
    //
    // int getMinAbsVal(final String fieldname, boolean reverseContains);
    //
    // int getMinTokenLen(final String fieldname, boolean reverseContains);
    //
    // int getMinimumTokenLen();
    //
    // double getFuzzyValue(final String fieldname, boolean reverseContains);

    ArrayList<String> getFields2Check();

    ArrayList<String> getFields2BIC();

    ArrayList<String> getFields2IBAN();

    ArrayList<String> getFuzzyFields();

    public LinkedHashMap<String, Queue<?>> getQueues();

    public boolean isFieldToCheck(final String fieldName, final String searchIndex, final String watchlist, final String messageType, boolean reverseContains);
    // fieldname in config (swift) ==
    // false <-> config in fieldname
    // (sepa) == true

    public boolean isField2BICTranslate(final String fieldName, final String messageType, boolean reverseContains);

    public boolean isField2IBANCheck(final String fieldName, final String messageType, boolean reverseContains);

    public boolean isField2ISINCheck(final String fieldName, final String messageType, boolean reverseContains);

    public boolean isField2Fuzzy(final String fieldName, final String messageType, boolean reverseContains);
}
