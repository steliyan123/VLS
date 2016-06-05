package de.dinkov.vlsapp.samples.about;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import de.dinkov.vlsapp.samples.backend.ElasticSearchSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField;
import eu.maxschuster.vaadin.autocompletetextfield.provider.CollectionSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.provider.MatchMode;
import eu.maxschuster.vaadin.autocompletetextfield.shared.ScrollBehavior;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import java.util.*;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

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


        AutocompleteSuggestionProvider suggestionProvider = new ElasticSearchSuggestionProvider(nodeBuilder());

        AutocompleteTextField searchInputField = new AutocompleteTextField();

        searchInputField.setCache(true); // Client side should cache suggestions
        searchInputField.setDelay(150); // Delay before sending a query to the server
        searchInputField.setItemAsHtml(false); // Suggestions contain html formating. If true, make sure that the html is save to use!
        searchInputField.setMinChars(2); // The required value length to trigger a query
        searchInputField.setScrollBehavior(ScrollBehavior.NONE); // The method that should be used to compensate scrolling of the page
        searchInputField.setSuggestionLimit(0); // The max amount of suggestions send to the client. If the limit is >= 0 no limit is applied

        searchInputField.addTextChangeListener(e -> {
            // Use this method only if you have to react to text changes
            // somewhere else immediately. You can always get the current value
            // of the textfield by calling field.getValue()

            String text = "Text changed to: " + e.getText();
            Notification.show(text, Notification.Type.TRAY_NOTIFICATION);

        });
        searchInputField.addValueChangeListener(e -> {
            String text = "Value changed to: " + e.getProperty().getValue();
            Notification notification = new Notification(
                    text, Notification.Type.TRAY_NOTIFICATION);
            notification.setPosition(Position.BOTTOM_LEFT);
            notification.show(Page.getCurrent());
        });
        searchInputField.setSuggestionProvider(suggestionProvider);
        searchInputField.setWidth(500,Unit.PIXELS);
        addComponent(searchInputField);
        setComponentAlignment(searchInputField, Alignment.MIDDLE_CENTER);

        addComponent(aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}