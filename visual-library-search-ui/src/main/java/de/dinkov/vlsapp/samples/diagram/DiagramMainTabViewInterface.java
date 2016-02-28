package de.dinkov.vlsapp.samples.diagram;

import de.dinkov.vlsapp.samples.backend.SearchSession;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.diagram.
 * Created by Steliyan Dinkov on 2/28/2016.
 */
public interface DiagramMainTabViewInterface {
    interface MainViewListener {
        void createNewSession(SearchSession newSearchSession);
    }

    void addListener(MainViewListener listener);

    void addNewSessionTab(SearchSession newSearchSession);
}