import org.borland.core.model.object.EObject;
import org.borland.core.model.property.EProperty;
import org.borland.core.model.worldcontext.ObjectWorldContext;
import org.borland.core.util.Vector3;
import org.borland.plugin.velocity.VelocityPlugin;
import org.borland.ui.WorldRenderMain;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VelocityTest {

    private WorldRenderMain app;

    @BeforeClass
    public void init() {
        app = new WorldRenderMain();
    }

    @Test
    public void startVelocityTest() {
        VelocityPlugin vPlugin = new VelocityPlugin();
        vPlugin.registerBehavior(app.getCore().getBehaviorManager());

        initObjects(app.getCore().getWorldContext().getObjectContext());

        app.startApplication();
    }

    private void initObjects(ObjectWorldContext objectContext) {
        objectContext.addObject(createObject(1, 0, 0));
        objectContext.addObject(createObject(0, 0.5, 0));
        objectContext.addObject(createObject(0, 0, 0.1));
        objectContext.addObject(createObject(-1, 0, 0));
        objectContext.addObject(createObject(0, -0.5, 0));
        objectContext.addObject(createObject(0, 0, -0.1));
    }

    private int currentObjectIndex = -1;
    private EObject createObject(double vx, double vy, double vz) {
        EProperty position = new EProperty("POSITION", "Position", new Vector3(0, 0, 0));
        EProperty velocity = new EProperty("VELOCITY", "Velocity", new Vector3(vx, vy, vz));

        currentObjectIndex++;
        EObject res = new EObject("obj" + currentObjectIndex);

        res.setProperty(position);
        res.setProperty(velocity);
        return res;
    }

}
