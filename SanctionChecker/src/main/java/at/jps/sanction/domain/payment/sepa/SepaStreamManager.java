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

}
