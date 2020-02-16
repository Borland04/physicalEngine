package org.borland.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import org.borland.ui.Main;

public class MainScreen implements Screen {
    private static final float FOV_Y = 90;

    private final Main parent;
    private PerspectiveCamera camera;
    private Model model;
    private ModelInstance instance;
    private ModelBatch modelBatch;
    private Environment environment;
    private CameraInputController cameraController;

    public MainScreen(Main parent) {
        this.parent = parent;
        modelBatch = new ModelBatch(); // TODO: get from parent
        initCamera();
        initEnvironment();
        createBox();
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

    private void createBox() {
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.VIOLET), ColorAttribute.createReflection(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);
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
        cameraController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        modelBatch.render(instance, environment);
        modelBatch.end();
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
        model.dispose();
    }

}
