package at.jps.sanction.domain.payment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.SystemUtils;

import at.jps.sanction.model.HitResult;

@Entity
@DiscriminatorValue("S")
public class PaymentHitResult extends HitResult implements Comparable<HitResult> {

    /**
     *
     */
    private static final long serialVersionUID = 641102584091207693L;

    private String            hitId;
    private String            hitExternalUrl;
    private String            hitLegalBasis;
    private String            hitListName;
    private String            hitRemark;
    private int               hitListPriority  = 99;

    private String            hitOptimized     = " ";

    public String getHitExternalUrl() {
        return hitExternalUrl;
    }

    public String getHitId() {
        return hitId;
    }

    public String getHitLegalBasis() {
        return hitLegalBasis;
    }

    public String getHitListName() {
        return hitListName;
    }

    public void setHitExternalUrl(final String hitExternalUrl) {
        this.hitExternalUrl = hitExternalUrl;
    }

    public void setHitId(final String hitId) {
        this.hitId = hitId;
    }

    public void setHitLegalBasis(final String hitLegalBasis) {
        this.hitLegalBasis = hitLegalBasis;
    }

    public void setHitListName(final String hitListName) {
        this.hitListName = hitListName;
    }

    @Override
    public String toString() {

        final StringBuilder content = new StringBuilder(super.toString());

        content.append("Listname: ").append(getHitListName()).append(SystemUtils.LINE_SEPARATOR).append("Listid  :  ").append(getHitId()).append(SystemUtils.LINE_SEPARATOR).append("Ext Url : ")
                .append(getHitExternalUrl()).append(SystemUtils.LINE_SEPARATOR).append("LegBas  : ").append(getHitLegalBasis()).append(SystemUtils.LINE_SEPARATOR).append("opiHint : ")
                .append(getHitOptimized());

        return content.toString();

    }

    public String getHitRemark() {
        return hitRemark;
    }

    public void setHitRemark(String hitRemark) {
        this.hitRemark = hitRemark;
    }

    public String getHitOptimized() {
        return hitOptimized;
    }

    public void setHitOptimized(String hitComment) {
        this.hitOptimized = hitComment;
    }

    public int getHitListPriority() {
        return hitListPriority;
    }

    public void setHitListPriority(int hitListPriority) {
        this.hitListPriority = hitListPriority;
    }

    // TODO order by Listpriority
    @Override
    public int compareTo(final HitResult other) {

        int comp = super.compareTo(other);

        if (comp == 0) {  // lower order == higher priority
            if (getHitListPriority() < ((PaymentHitResult) other).getHitListPriority()) {
                comp = -1;
            }
            else {
                if (getHitListPriority() > ((PaymentHitResult) other).getHitListPriority()) {
                    comp = 1;
                }
            }
        }
        return comp;
    }

}
