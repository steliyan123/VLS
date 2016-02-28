package de.dinkov.vlsapp.samples.diagram;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import de.dinkov.vlsapp.samples.backend.SearchSession;

import java.util.ArrayList;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.diagram.
 * Created by Steliyan Dinkov on 2/28/2016.
 */
public class DiagramMainTabView extends VerticalLayout implements View, DiagramMainTabViewInterface  {

    private static final long serialVersionUID = 1L;

    private TabSheet sessionTabBar = new TabSheet();
    ArrayList<SearchSession> searchSessionList = new ArrayList<>();
    private MainViewListener listener;
    private MainViewPresenter mainViewPresenter;

    HorizontalLayout hl;
    public String BROWSER_WIDTH = String.valueOf(Page.getCurrent().getBrowserWindowWidth());
    public String BROWSER_HEIGHT = String.valueOf(Page.getCurrent().getBrowserWindowHeight());
    public int BROWSER_WIDTH_int = Page.getCurrent().getBrowserWindowWidth();
    public int BROWSER_HEIGHT_int = Page.getCurrent().getBrowserWindowHeight();

    public DiagramMainTabView() {
        setImmediate(true);
        setSizeFull();

        Page.getCurrent()
            .addBrowserWindowResizeListener(
                new Page.BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            Page.BrowserWindowResizeEvent event) {
                        BROWSER_WIDTH = String.valueOf(Page.getCurrent()
                                .getBrowserWindowWidth());
                        BROWSER_HEIGHT = String.valueOf(Page.getCurrent()
                                .getBrowserWindowHeight());
                        BROWSER_WIDTH_int = Page.getCurrent()
                                .getBrowserWindowWidth();
                        BROWSER_HEIGHT_int = Page.getCurrent()
                                .getBrowserWindowHeight();

                        hl.setHeight(BROWSER_HEIGHT);
                    }
                });

        sessionTabBar = new TabSheet();
        sessionTabBar.setSizeFull();
        sessionTabBar.setImmediate(true);

        SearchSession searchSession = new SearchSession();
        searchSessionList.add(searchSession);

        mainViewPresenter = new MainViewPresenter(this);

        addSessionTabBar();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) { }

    private void addSessionTabBar() {

        hl = new HorizontalLayout();
        hl.setWidth(BROWSER_WIDTH);
        hl.setHeight(String.valueOf(BROWSER_HEIGHT_int - 30));

        sessionTabBar.addTab(new Label(" "), "+");
        sessionTabBar.addSelectedTabChangeListener(
            new TabSheet.SelectedTabChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
                TabSheet tabsheet = event.getTabSheet();
                if (tabsheet.getSelectedTab().getClass() == Label.class) {
                    Label newSession = (Label) tabsheet.getSelectedTab();
                    if (newSession.getDescription() == "") {
                        // @todo bind with search data from plot clicks & co.
                        listener.createNewSession(new SearchSession());
                    }
                }
            }
        });

        hl.addComponent(sessionTabBar);

        addComponent(hl);
    }

    @Override
    public void addListener(MainViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void addNewSessionTab(SearchSession newSearchSession) {
        searchSessionList.add(newSearchSession);

        DiagramView diagramView = new DiagramView();
        diagramView.setSizeFull();

        sessionTabBar.addTab(diagramView, "Session " + sessionTabBar.getComponentCount());
        sessionTabBar.setSelectedTab(diagramView);
    }
}
