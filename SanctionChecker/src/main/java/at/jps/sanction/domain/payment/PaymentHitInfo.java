package at.jps.sanction.domain.payment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import at.jps.sanction.model.WordHitInfo;

@Entity
@DiscriminatorValue("S")
public class PaymentHitInfo extends WordHitInfo {

    /**
     * 
     */
    private static final long serialVersionUID = -9095912729534026407L;
    private String            sanctionListName;
    private String            sanctionListId;

    public PaymentHitInfo() {
        super();
    }

    public PaymentHitInfo(final String sanctionListName, final String sanctionListId, final String token, final String fieldName, final String fieldText, final int value) {
        super(token, fieldName, fieldText, value);

        setSanctionListId(sanctionListId);
        setSanctionListName(sanctionListName);
    }

    public String getSanctionListName() {
        return sanctionListName;
    }

    public void setSanctionListName(String sanctionListName) {
        this.sanctionListName = sanctionListName;
    }

    public String getSanctionListId() {
        return sanctionListId;
    }

    public void setSanctionListId(String sanctionListId) {
        this.sanctionListId = sanctionListId;
    }

    @Override
    public String toString() {
        final StringBuilder stringbuilder = new StringBuilder();

        stringbuilder.append(super.toString()).append(" Watchlist: ").append(getSanctionListName()).append(" Id: " + getSanctionListId());

        return stringbuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {

        boolean eq = false;

        if (obj != null) {
            eq = super.equals(obj);

            if ((eq) && (((PaymentHitInfo) obj).getSanctionListName() != null)) {
                eq = getSanctionListName().equals(((PaymentHitInfo) obj).getSanctionListName());
                if ((eq) && (((PaymentHitInfo) obj).getSanctionListId() != null)) {
                    eq = getSanctionListId().equals(((PaymentHitInfo) obj).getSanctionListId());
                }
            }
        }
        return eq;
    }

}
