package de.dinkov.vlsapp.samples.interfaces;

import de.dinkov.vlsapp.samples.backend.model.Author;
import de.dinkov.vlsapp.samples.backend.model.Document;
import de.dinkov.vlsapp.samples.backend.model.SearchModel;
import de.dinkov.vlsapp.samples.backend.model.Strategy;
import de.dinkov.vlsapp.samples.helpclasses.ImportantPoints;
import java.awt.*;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.interfaces.
 * Created by Steliyan Dinkov on 1/30/2016.
 */
public interface ViewPort {

    interface ViewPortListener {

        void applySearchFormStrategySearch(Strategy strategy, String keywords,
                                           Point location, ImportantPoints importantPoints, int parentID);

        void applyPersonStategy(Strategy s, Author author,
                                Point location, ImportantPoints importantPoints, int parentID);

        void applyDocumentStategy(Strategy strategy, Document mainDoc,
                                  Point location, ImportantPoints startingPoints, int parentID);

    }
    void addListener(ViewPortListener listener);

    void createNewView(Strategy strategy, SearchModel searchModel,
                       Point location, ImportantPoints importantPoints, int parentID);
}

