import org.borland.core.model.object.EObject;
import org.borland.core.model.property.EProperty;
import org.borland.core.model.worldcontext.ObjectWorldContext;
import org.borland.core.util.Vector3;
import org.borland.plugin.velocity.VelocityPlugin;
import org.borland.ui.form.Main;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class VelocityTest {

    private Main app;

    @BeforeClass
    public void init() {
        Main.main(new String[] {});
        app = Main.mainInstance;
    }

    @Test
    public void startVelocityTest() throws IOException {
        VelocityPlugin vPlugin = new VelocityPlugin();
        vPlugin.registerBehavior(app.getWorld().getBehaviorManager());

        initObjects(app.getWorld().getWorldContext().getObjectContext());
        System.in.read(); // To not to close the window right now
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
