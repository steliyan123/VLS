package de.dinkov.vlsapp.samples.diagram;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.diagram.
 * Created by Steliyan Dinkov on 2/28/2016.
 */

import de.dinkov.vlsapp.samples.backend.SearchSession;

public class MainViewPresenter implements DiagramMainTabViewInterface.MainViewListener {

    DiagramMainTabView mainTabView;

    public MainViewPresenter(DiagramMainTabView mainview) {
        mainTabView = mainview;
        mainTabView.addListener(this);
        createNewSession(new SearchSession());
    }

    @Override
    public void createNewSession(SearchSession newSearchSession) {
        mainTabView.addNewSessionTab(newSearchSession); // pass model later to viewport
    }
}