package at.jps.sanction.domain.payment.sepa;

import at.jps.sanction.domain.payment.PaymentStreamManager;

public class SepaStreamManager extends PaymentStreamManager {

    @Override
    public boolean isFieldToCheck(final String fieldName, final String listName, final String entityType, final String entityCategory) {
        return getStreamConfig().isFieldToCheck(fieldName, listName, entityType, entityCategory, true);
    }

    @Override
    public boolean isField2BICTranslate(final String fieldName) {
        return getStreamConfig().isField2BICTranslate(fieldName, true);
    }

    @Override
    public boolean isField2IBANCheck(final String fieldName) {
        return (getStreamConfig().isField2IBANCheck(fieldName, true) || fieldName.contains("/IBAN"));
    }

    @Override
    public boolean isField2Fuzzy(final String fieldName) {
        return getStreamConfig().isField2Fuzzy(fieldName, true);
    }

    @Override
    public int getMinAbsVal(final String fieldname) {
        return getStreamConfig().getMinAbsVal(fieldname, false);
    }

    @Override
    public int getMinRelVal(final String fieldname) {
        return getStreamConfig().getMinRelVal(fieldname, false);
    }

    @Override
    public int getMinTokenLen(final String fieldname) {
        return getStreamConfig().getMinTokenLen(fieldname, false);
    }

    @Override
    public double getFuzzyValue(final String fieldname) {
        return getStreamConfig().getFuzzyValue(fieldname, false);
    }

}
