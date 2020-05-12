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
import org.borland.core.model.object.EObject;
import org.borland.core.util.Vector3;
import org.borland.ui.WorldRenderMain;

import java.util.List;

public class WorldRenderScreen implements Screen {
    private static final float FOV_Y = 90;

    private final MainScreen parent;
    private Model model;

    public WorldRenderScreen(MainScreen parent) {
        this.parent = parent;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        updateWorld(delta);

        renderWorld(parent.getModelBatch());
    }

    private void updateWorld(float delta) {
        parent.getCore().tick(delta);
    }

    /**
     * Should be called after 'modelBatch.begin' and before 'modelBatch.end'
     * @param modelBatch
     */
    private void renderWorld(ModelBatch modelBatch) {
        List<EObject> objects = parent.getCore().getWorldContext().getObjectContext().getObjects();
        for(EObject obj: objects) {
            obj.getProperty("POSITION")
                    .ifPresent(posProp -> {
                        Vector3 pos = posProp.getValue(Vector3.class);
                        ModelInstance instance = createBox(pos.getX(), pos.getY(), pos.getZ());
                        modelBatch.render(instance, parent.getEnvironment());
                    });
        }
    }

    private ModelInstance createBox(double x, double y, double z) {
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.ORANGE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        var result = new ModelInstance(model);
        result.transform.setToTranslation((float)x, (float)y, (float)z);
        return result;
    }


    @Override
    public void resize(int width, int height) {
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
        model.dispose();
    }

}
