package org.borland.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.borland.core.EngineCore;
import org.borland.ui.screens.MainScreen;

public class WorldRenderMain extends Game {

    private EngineCore core = new EngineCore();

    public static void main(String[] args) {
        WorldRenderMain app = new WorldRenderMain();
        app.startApplication();
    }

    public void startApplication() {
        var config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("My app");
        config.setWindowedMode(800, 600);
        config.setMaximized(true);
        new Lwjgl3Application(this, config);
    }

    @Override
    public void create() {
        this.setScreen(new MainScreen(this));
    }

    public EngineCore getCore() {
        return core;
    }
}
