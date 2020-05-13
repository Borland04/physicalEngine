package org.borland.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.borland.core.EngineCore;
import org.borland.ui.screens.MainScreen;

public class WorldRenderMain extends Game {

    private EngineCore core = new EngineCore();

    public static void main(String[] args) {
        WorldRenderMain app = new WorldRenderMain();
        app.startApplication();
    }

    public LwjglAWTCanvas startApplication() {
        return new LwjglAWTCanvas(this);
    }

    @Override
    public void create() {
        this.setScreen(new MainScreen(this));
    }

    public EngineCore getCore() {
        return core;
    }
}
