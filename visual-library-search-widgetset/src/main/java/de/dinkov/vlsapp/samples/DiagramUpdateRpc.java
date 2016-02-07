package de.dinkov.vlsapp.samples;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.
 * Created by Steliyan Dinkov on 2/7/2016.
 */

import com.vaadin.shared.communication.ClientRpc;

public interface DiagramUpdateRpc extends ClientRpc {
    public void updateTree(String treedata);
}