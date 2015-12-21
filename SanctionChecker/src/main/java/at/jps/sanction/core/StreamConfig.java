package at.jps.sanction.core;

import java.util.ArrayList;

public interface StreamConfig {

    int getMinRelVal();

    int getMinAbsVal();

    int getMinTokenLen();

    double getFuzzyValue();

    double getFuzzyVal();

    ArrayList<String> getFields2Check();

    ArrayList<String> getFields2BIC();

    ArrayList<String> getFuzzyFields();

}
