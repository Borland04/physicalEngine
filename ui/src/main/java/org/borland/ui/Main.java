package org.borland.ui;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.borland.core.EngineCore;
import org.borland.ui.screens.WorldRenderScreen;

public class Main extends Game {

    public EngineCore core = new EngineCore();

    public static void main(String[] args) {
        Main app = new Main();
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
        this.setScreen(new WorldRenderScreen(this));
    }
}
