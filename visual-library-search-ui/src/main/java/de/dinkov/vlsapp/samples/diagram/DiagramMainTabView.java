package de.dinkov.vlsapp.samples.diagram;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import de.dinkov.vlsapp.samples.backend.Entities.SearchModel;
import de.dinkov.vlsapp.samples.backend.SearchSession;
import de.dinkov.vlsapp.samples.search.SearchView;

import java.util.HashMap;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.diagram.
 * Created by Steliyan Dinkov on 2/28/2016.
 */
public class DiagramMainTabView extends VerticalLayout implements View, DiagramMainTabViewInterface  {

    private static final long serialVersionUID = 1L;

    private TabSheet sessionTabBar = new TabSheet();
    HashMap<String, DiagramView> searchSessionList = new HashMap<>();
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
                Component selectedTab = tabsheet.getSelectedTab();
                Class<?> c = selectedTab.getClass();
                if (c == Label.class) {
                    Label newSession = (Label) tabsheet.getSelectedTab();
                    if (newSession.getDescription() == "") {
                        addNewSessionTab(new SearchSession().getDefaultSearchSession());
                    }
                } else if (c == DiagramView.class) {
                    int index = tabsheet.getTabIndex();
                    String diagramViewSessionTabId = selectedTab.getId();
                    DiagramView diagramViewSaved = null;
                    if (searchSessionList.containsKey(diagramViewSessionTabId)) {
                        diagramViewSaved = searchSessionList.get(diagramViewSessionTabId);
                    }
                    DiagramView diagramView = (DiagramView) selectedTab;
                    SearchView searchView = diagramView.getSearchView();
                    SearchModel searchModel = searchView.getLastSearch();
                    SearchModel searchModelSaved = null;
                    if (diagramViewSaved != null) {
                        searchModelSaved = diagramViewSaved.getSearchView().getLastSearch();
                    }

                    String result = "";
                    if (searchModelSaved != null) {
                        result = diagramView.getSearchView().getJsonResult(
                            searchModelSaved.getStrategy().toString(),
                            searchModelSaved.getKeywords(),
                            "name"
                        );
                    } else {
                        result = searchView.getJsonResult(
                            searchModel.getStrategy().toString(),
                            searchModel.getKeywords(),
                            "name"
                        );
                    }

                    System.out.println("{SELECTED_TAB_CHANGE}: " + result);

                    searchView.setTreeData(result);
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

        DiagramView diagramView = new DiagramView(newSearchSession);
        diagramView.setSizeFull();

        String lala = String.valueOf(sessionTabBar.getTabIndex());
        String lulu = sessionTabBar.getCaption();

        sessionTabBar.addTab(diagramView, "Session " + sessionTabBar.getComponentCount());
        sessionTabBar.setSelectedTab(diagramView);

        String id = String.valueOf(sessionTabBar.getTabIndex());
        sessionTabBar.getSelectedTab().setId(id);
        searchSessionList.put(id, diagramView);
    }
}
