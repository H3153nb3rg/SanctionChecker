package at.jps.sanction.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import at.jps.sanction.model.queue.Queue;

public interface StreamConfig {

    int getMinRelVal();

    int getMinAbsVal();

    int getMinTokenLen();

    double getFuzzyValue();

    double getFuzzyVal();

    ArrayList<String> getFields2Check();

    ArrayList<String> getFields2BIC();

    ArrayList<String> getFields2IBAN();

    ArrayList<String> getFuzzyFields();

    public LinkedHashMap<String, Queue<?>> getQueues();

    public boolean isFieldToCheck(final String fieldName, final String listName, final String EntityType, boolean reverseContains);  // fieldname in config (swift) == false <-> config in fieldname
                                                                                                                                     // (sepa) == true

    public boolean isField2BICTranslate(final String fieldName, boolean reverseContains);

    public boolean isField2IBANCheck(final String fieldName, boolean reverseContains);

    public boolean isField2Fuzzy(final String fieldName, boolean reverseContains);
}
