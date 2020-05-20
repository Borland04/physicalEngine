package org.borland.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import org.borland.core.EngineCore;
import org.borland.ui.WorldRenderMain;
import org.borland.ui.model.WorldState;

public class MainScreen implements Screen {
    private static final float FOV_Y = 90;

    private final WorldRenderMain parent;
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private Environment environment;
    private CameraInputController cameraController;

    private final WorldRenderScreen worldRenderer;
    private final HUDRenderScreen hudRenderer;


    public MainScreen(WorldRenderMain parent) {
        this.parent = parent;
        modelBatch = new ModelBatch(); // TODO: get from parent
        initCamera();
        initEnvironment();

        worldRenderer = new WorldRenderScreen(this);
        hudRenderer = new HUDRenderScreen(this);
    }

    private void initCamera() {
        camera = new PerspectiveCamera(FOV_Y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        cameraController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(cameraController);
    }

    private void initEnvironment() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        cameraController.update();

        modelBatch.begin(camera);
        worldRenderer.render(delta);
        modelBatch.end();

        hudRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public ModelBatch getModelBatch() {
        return modelBatch;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public CameraInputController getCameraController() {
        return cameraController;
    }

    public WorldState getWorldState() {
        return parent.getWorldState();
    }
}
