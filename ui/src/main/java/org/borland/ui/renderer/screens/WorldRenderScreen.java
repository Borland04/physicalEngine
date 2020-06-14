package org.borland.ui.renderer.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import org.borland.core.model.object.EObject;
import org.borland.core.util.Vector3;

import java.util.List;

// TODO: logging
public class WorldRenderScreen implements Screen {

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
        renderWorld(parent.getModelBatch());
    }

    /**
     * Should be called between 'modelBatch.begin' and 'modelBatch.end'
     * @param modelBatch
     */
    private void renderWorld(ModelBatch modelBatch) {
        List<EObject> objects = parent.getWorldState().getWorld().getWorldContext().getObjectContext().getObjects();
        for(EObject obj: objects) {
            renderObject(obj, modelBatch);
        }
    }

    private void renderObject(EObject object, ModelBatch modelBatch) {
        object.getProperty("POSITION")
                .ifPresent(posProp -> {
                    Vector3 pos = posProp.getValue(Vector3.class);
                    ModelInstance instance = createBox(pos.getX(), pos.getY(), pos.getZ());
                    modelBatch.render(instance, parent.getEnvironment());
                });
    }

    private ModelInstance createBox(double x, double y, double z) {
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createSphere(5f, 5f, 5f, 50, 50,
                new Material(ColorAttribute.createDiffuse(Color.ORANGE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance result = new ModelInstance(model);
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
