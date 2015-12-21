package at.jps.sanction.domain.sepa;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import at.jps.sanction.domain.SanctionStreamManager;

public class SepaStreamManager extends SanctionStreamManager {

    private List<String> supportedMessageTypes;

    public List<String> getSupportedMessageTypes() {
        if (supportedMessageTypes == null) {
            supportedMessageTypes = new ArrayList<String>();

            final String types = getProperties().getProperty(getStreamName() + ".msgTypes");

            final StringTokenizer tokenizer = new StringTokenizer(types, ",");

            while (tokenizer.hasMoreTokens()) {
                supportedMessageTypes.add(tokenizer.nextToken());
            }

        }

        return supportedMessageTypes;
    }

    @Override
    public boolean isFieldToCheck(final String fieldName, final String listName) {
        return true;
    }

    @Override
    public boolean isField2BICTranslate(final String fieldName) {
        return fieldName.contains("/BIC");
    }

    @Override
    public boolean isFildIBAN(final String fieldName) {
        return fieldName.contains("/IBAN");
    }

}
