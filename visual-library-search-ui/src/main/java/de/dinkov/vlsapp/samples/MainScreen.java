package de.dinkov.vlsapp.samples;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import de.dinkov.vlsapp.VlsAppUI;
import de.dinkov.vlsapp.samples.about.AboutView;
import de.dinkov.vlsapp.samples.crud.SampleCrudView;
import de.dinkov.vlsapp.samples.diagram.DiagramMainTabView;
import de.dinkov.vlsapp.samples.diagram.DiagramView;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends VerticalLayout {
    private Menu menu;

    public MainScreen(VlsAppUI ui) {

        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new Menu(navigator);
        menu.addView(new SampleCrudView(), SampleCrudView.VIEW_NAME,
                SampleCrudView.VIEW_NAME, FontAwesome.EDIT);
        menu.addView(new DiagramMainTabView(), DiagramView.VIEW_NAME, DiagramView.VIEW_NAME,
                FontAwesome.INFO_CIRCLE);
        menu.addView(new AboutView(), AboutView.VIEW_NAME, AboutView.VIEW_NAME,
                FontAwesome.INFO_CIRCLE);
       /* menu.addView(new SearchView(), SearchView.VIEW_NAME, SearchView.VIEW_NAME,
                FontAwesome.INFO_CIRCLE);*/
        navigator.addViewChangeListener(viewChangeListener);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
    }

    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            menu.setActiveView(event.getViewName());
        }

    };
}
