package at.jps.slcm.gui.views;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import at.jps.sanction.model.ProcessStep;
import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.util.SessionInfo;
import at.jps.slcm.gui.views.payment.TransactionHitHandling;

public class HitHandlingCoreView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long   serialVersionUID = -7550009752493474506L;

    final public static String  ViewName         = "HitHandling";

    private Button              buttonNext;
    private Button              buttonPrev;
    private Button              buttonHit;
    private Button              buttonNoHit;
    private Button              buttonPostpone;

    private final AdapterHelper guiAdapter;

    public static String        newline          = System.getProperty("line.separator");
    // -----------------------------

    // implementation is "active Stream" dependent
    private HitHandlingView     hitHandlingView  = null;

    public HitHandlingCoreView(final AdapterHelper guiAdapter) {
        super();
        this.guiAdapter = guiAdapter;

        // TODO: TESTDUMMY -> this has to be done dynamically
        hitHandlingView = new TransactionHitHandling(guiAdapter);

        buildPage();
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        // Notification.show("Work on!");

        // check if Stream has changed !!
        final SessionInfo si = (SessionInfo) getUI().getSession().getAttribute(SessionInfo.SESSION_INFO);

        if (si != null) {
            guiAdapter.setActiveStreamName(si.getActiveStream());

            // if changed change also the embedded view for message handling
        }

        doNextMessage(true);
    }

    private void checkAndGetNextMessage(final boolean next) {

        if ((next && guiAdapter.getHitBuffer().hasNextMessage()) || (!next && guiAdapter.getHitBuffer().hasPrevMessage())) {
            guiAdapter.setCurrentMessage((next ? guiAdapter.getHitBuffer().getNextMessage() : guiAdapter.getHitBuffer().getPrevMessage()));

            if (guiAdapter.getCurrentMessage() != null) {
            }
        }
    }

    private void doNextMessage(final boolean next) {

        checkAndGetNextMessage(next);

        if (guiAdapter.getCurrentMessage() != null) {

            onNextMessage();
        }
        updateButtonStatus();
    }

    private void onNextMessage() {

        // TODO: Check Type of Message -> Check if we need to change Implementation -> Switch Implementation & view !!

        // call embedded view to refresh
        if (hitHandlingView != null) {
            hitHandlingView.onNextMessage();
        }

    }

    private void updateButtonStatus() {
        buttonPostpone.setEnabled(guiAdapter.getCurrentMessage() != null);
        buttonHit.setEnabled(guiAdapter.getCurrentMessage() != null);
        buttonNoHit.setEnabled(guiAdapter.getCurrentMessage() != null);
        buttonPrev.setEnabled(guiAdapter.getCurrentMessage() != null);
        buttonNext.setEnabled(guiAdapter.getCurrentMessage() != null);
    }

    private void buildPage() {

        buttonPostpone = new Button("postpone");
        buttonPostpone.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 3734735406147554187L;

            @Override
            public void buttonClick(final ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(UI.getCurrent(), "Postpone Decision", new ConfirmDialog.Listener() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = 853673169272603L;

                    @Override
                    public void onClose(final ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            doPostpone();
                        }
                        else {
                            // User did not confirm
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("not confirmed!!"));
                        }
                    }
                });

            }
        });
        buttonNoHit = new Button("No Hit");
        buttonNoHit.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -3173345285232527353L;

            @Override
            public void buttonClick(final ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(UI.getCurrent(), "Confirm NoHit", new ConfirmDialog.Listener() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = 1001564814192572881L;

                    @Override
                    public void onClose(final ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("confirmed!!"));

                            doProcessNoHit();
                        }
                        else {
                            // User did not confirm
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("not confirmed!!"));
                        }
                    }
                });

            }
        });
        buttonHit = new Button("Hit");
        buttonHit.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 5263732114286684471L;

            @Override
            public void buttonClick(final ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(UI.getCurrent(), "Confirm Hit", new ConfirmDialog.Listener() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = -6745664926081052916L;

                    @Override
                    public void onClose(final ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("confirmed!!"));

                            doProcessHit();

                        }
                        else {
                            // User did not confirm
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("not confirmed!!"));
                        }
                    }
                });

            }
        });

        buttonPrev = new Button("Previous");
        buttonPrev.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -6045113687827684393L;

            @Override
            public void buttonClick(final ClickEvent event) {

                doNextMessage(false);

            }
        });
        buttonNext = new Button("Next");
        buttonNext.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -2221502598602123148L;

            @Override
            public void buttonClick(final ClickEvent event) {

                doNextMessage(true);

            }
        });

        final HorizontalLayout actions = new HorizontalLayout(buttonPostpone, buttonHit, buttonHit, buttonPrev, buttonNext);
        actions.setWidth("80%");
        actions.setMargin(true);

        // TODO: This cannot work in real life as we know only after the first message of the TYPE we are dealing with !!

        final Component hitHandlingComponent = hitHandlingView.buildPage();

        addComponent(hitHandlingComponent);
        addComponent(actions);

        setExpandRatio(hitHandlingComponent, 9);
        setExpandRatio(actions, 1);
        // setMargin(new MarginInfo(true));
        setSizeFull();

    }

    // private void feedback(boolean confirmed) {
    // Notification.show("Confirmed:" + confirmed);
    // }
    //
    // ----------------------------------
    private void addProcessStep(final String text) {
        final ProcessStep processStep = new ProcessStep();
        processStep.setRemark(text);
        guiAdapter.getCurrentMessage().addProcessStep(processStep);

    }

    private void doProcessHit() {
        if (hitHandlingView != null) {
            hitHandlingView.doProcessHit();
        }

        if (guiAdapter.getConfig().isAutolearnMode()) {

            guiAdapter.addToPostProcessHitQueue(guiAdapter.getCurrentMessage());
            addProcessStep("Autolearned Hit");
        }
        else {
            addProcessStep("Confirmed Hit");
            guiAdapter.addToFinalHitQueue(guiAdapter.getCurrentMessage());
        }
        doNextMessage(true);

    }

    private void doProcessNoHit() {

        if (hitHandlingView != null) {
            hitHandlingView.doProcessNoHit();
        }

        if (guiAdapter.getConfig().isAutolearnMode()) {
            guiAdapter.addToPostProcessNoHitQueue(guiAdapter.getCurrentMessage());
            addProcessStep("Autolearned Miss");
        }
        else {
            addProcessStep("Confirmed Miss");
            guiAdapter.addToFinalNoHitQueue(guiAdapter.getCurrentMessage());
        }
        doNextMessage(true);
    }

    private void doPostpone() {
        if (hitHandlingView != null) {
            hitHandlingView.doPostpone();
        }

        addProcessStep("Backlogged");

        guiAdapter.addToBacklogQueue(guiAdapter.getCurrentMessage());
    }

}
