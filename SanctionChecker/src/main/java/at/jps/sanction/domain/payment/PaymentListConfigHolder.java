package at.jps.sanction.domain.payment;

import at.jps.sanction.core.ListConfigHolder;
import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sanction.model.listhandler.OptimizationListHandler;

public class PaymentListConfigHolder extends ListConfigHolder {

    private OptimizationListHandler txNoHitOptimizationListHandler;
    private OptimizationListHandler txHitOptimizationListHandler;
    private NoWordHitListHandler    noWordHitListHandler;

    public PaymentListConfigHolder() {
        super();
    }

    public OptimizationListHandler getTxNoHitOptimizationListHandler() {
        return txNoHitOptimizationListHandler;
    }

    public void setTxNoHitOptimizationListHandler(final OptimizationListHandler optimizationListHandler) {
        txNoHitOptimizationListHandler = optimizationListHandler;
    }

    public OptimizationListHandler getTxHitOptimizationListHandler() {
        return txHitOptimizationListHandler;
    }

    public void setTxHitOptimizationListHandler(final OptimizationListHandler optimizationListHandler) {
        txHitOptimizationListHandler = optimizationListHandler;
    }

    public NoWordHitListHandler getNoWordHitListHandler() {
        return noWordHitListHandler;
    }

    public void setNoWordHitListHandler(final NoWordHitListHandler noWordHitListHandler) {
        this.noWordHitListHandler = noWordHitListHandler;
    }

}
