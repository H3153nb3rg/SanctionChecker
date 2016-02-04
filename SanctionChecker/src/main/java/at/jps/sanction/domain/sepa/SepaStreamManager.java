package at.jps.sanction.domain.sepa;

import java.util.List;

import at.jps.sanction.domain.SanctionStreamManager;

public class SepaStreamManager extends SanctionStreamManager {

    private boolean internalIsTokenInFieldnameCheck(final List<String> fields, final String fieldName) {
        boolean isfield = false;

        for (String fieldToken : fields) {
            if (fieldName.contains(fieldToken)) {
                isfield = true;
                break;
            }
        }
        return isfield;
    }

    @Override
    public boolean isFieldToCheck(final String fieldName, final String listName) {

        boolean isfield = internalIsTokenInFieldnameCheck(getAllFieldsToCheck(), fieldName);
        return isfield;
    }

    @Override
    public boolean isField2BICTranslate(final String fieldName) {

        boolean isfield = internalIsTokenInFieldnameCheck(getAllFieldsToBIC(), fieldName);
        return isfield;
    }

    @Override
    public boolean isFieldIBAN(final String fieldName) {
        return fieldName.contains("/IBAN");  // TODO: take from conf
    }

    @Override
    public boolean isField2Fuzzy(String fieldName) {
        // TODO Auto-generated method stub
        return super.isField2Fuzzy(fieldName);
    }
}
