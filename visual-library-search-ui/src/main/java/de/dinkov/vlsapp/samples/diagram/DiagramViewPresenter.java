package de.dinkov.vlsapp.samples.diagram;

import de.dinkov.vlsapp.VlsAppUI;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.diagram.
 * Created by Steliyan Dinkov on 1/29/2016.
 */
public class DiagramViewPresenter {

    private DiagramView view;

    public DiagramViewPresenter(DiagramView diagramView){

        this.view = diagramView;
    }

    public void init() {
       /* editProduct(null);
        // Hide and disable if not admin
        if (!VlsAppUI.get().getAccessControl().isUserInRole("admin")) {
            view.setNewProductEnabled(false);
        }

        view.showProducts(DataService.get().getAllProducts());*/
    }

}
