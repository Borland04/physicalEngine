package org.borland.ui;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.borland.ui.screens.MainScreen;

public class Main extends Game {

    public static void main(String[] args) {
        var config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("My app");
        config.setWindowedMode(800, 600);
        config.setMaximized(true);
        new Lwjgl3Application(new Main(), config);
    }

    @Override
    public void create() {
        this.setScreen(new MainScreen(this));
    }
}
