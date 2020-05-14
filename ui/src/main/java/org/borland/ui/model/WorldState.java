package org.borland.ui.model;

import org.borland.core.EngineCore;

public class WorldState {
    private EngineCore world;
    private SelectionManager selectionManager = new SelectionManager();

    public WorldState(EngineCore world) {
        this.world = world;
    }

    public EngineCore getWorld() {
        return world;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }
}
