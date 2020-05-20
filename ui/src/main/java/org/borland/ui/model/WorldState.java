package org.borland.ui.model;

import org.borland.core.EngineCore;

public class WorldState {
    private EngineCore world;
    private boolean isRunning = false;

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

    public void pause() {
        isRunning = false;
    }

    public void run() {
        isRunning = true;
    }

    public void toggleRunning() {
        isRunning = !isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
