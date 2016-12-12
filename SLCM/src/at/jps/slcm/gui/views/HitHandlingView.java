package at.jps.slcm.gui.views;

import com.vaadin.ui.Component;

public interface HitHandlingView {

    public Component buildPage();

    void onNextMessage();

    void doProcessHit();

    void doProcessNoHit();

    void doPostpone();

}
