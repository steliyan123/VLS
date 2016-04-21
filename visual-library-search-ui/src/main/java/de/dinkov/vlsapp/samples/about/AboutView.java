package de.dinkov.vlsapp.samples.about;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField;
import eu.maxschuster.vaadin.autocompletetextfield.provider.CollectionSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.provider.MatchMode;
import eu.maxschuster.vaadin.autocompletetextfield.shared.ScrollBehavior;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

public class AboutView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "About";

    public AboutView() {
        CustomLayout aboutContent = new CustomLayout("aboutview");
        aboutContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        aboutContent.addComponent(
                new Label(FontAwesome.INFO_CIRCLE.getHtml()
                        + " This application is using Vaadin "
                        + Version.getFullVersion(), ContentMode.HTML), "info");

        setSizeFull();
        setStyleName("about-view");


        // ===============================
        // Testing Autocomplete Field
        // ===============================

        Collection<String> theJavas = Arrays.asList(new String[] {
                "Java",
                "JavaScript",
                "Join Java",
                "JavaFX Script",
                "JavaFX is hard",
                "Something Java",
                "Another thing Java"
        });

        AutocompleteSuggestionProvider suggestionProvider = new CollectionSuggestionProvider(theJavas, MatchMode.CONTAINS, true, Locale.US);

        AutocompleteTextField field = new AutocompleteTextField();


        field.setCache(false); // Client side should cache suggestions
        field.setDelay(50); // Delay before sending a query to the server
        field.setItemAsHtml(false); // Suggestions contain html formating. If true, make sure that the html is save to use!
        field.setMinChars(1); // The required value length to trigger a query
        field.setScrollBehavior(ScrollBehavior.NONE); // The method that should be used to compensate scrolling of the page
        field.setSuggestionLimit(0); // The max amount of suggestions send to the client. If the limit is >= 0 no limit is applied

        field.setSuggestionProvider(suggestionProvider);
        field.addTextChangeListener(e -> {
            String text = "Text changed to: " + e.getText();
            Notification.show(text, Notification.Type.TRAY_NOTIFICATION);
        });
        field.addValueChangeListener(e -> {
            String text = "Value changed to: " + e.getProperty().getValue();
            Notification notification = new Notification(
                    text, Notification.Type.TRAY_NOTIFICATION);
            notification.setPosition(Position.BOTTOM_LEFT);
            notification.show(Page.getCurrent());
        });

        addComponent(field);
        setComponentAlignment(field, Alignment.MIDDLE_CENTER);
        // ===============================
        // Testing Autocomplete Field ENDS
        // ===============================
        addComponent(aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);

}

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
