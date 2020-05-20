package org.borland.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import org.borland.core.EngineCore;
import org.borland.ui.model.WorldState;
import org.borland.ui.screens.MainScreen;

public class WorldRenderMain extends Game {

    private WorldState worldState;
    private LwjglAWTCanvas worldCanvas;

    public static void main(String[] args) {
        WorldRenderMain app = new WorldRenderMain(new WorldState(new EngineCore()));
        app.getWorldState().run();
    }

    public WorldRenderMain(WorldState worldState) {
        this.worldState = worldState;
        worldCanvas = new LwjglAWTCanvas(this);
    }

    @Override
    public void create() {
        this.setScreen(new MainScreen(this));
    }

    public WorldState getWorldState() {
        return worldState;
    }

    public LwjglAWTCanvas getWorldCanvas() {
        return worldCanvas;
    }
}
