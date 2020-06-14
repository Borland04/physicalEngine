import org.borland.core.model.object.EObject;
import org.borland.core.model.property.EProperty;
import org.borland.core.model.worldcontext.ObjectWorldContext;
import org.borland.core.util.Vector3;
import org.borland.plugin.chargedparticle.ChargedParticleIds;
import org.borland.plugin.chargedparticle.ChargedParticlePlugin;
import org.borland.plugin.chargedparticle.model.ParticleType;
import org.borland.plugin.velocity.VelocityPlugin;
import org.borland.ui.form.Main;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class ChargedParticleTest {

  private Main app;


  @BeforeClass
  public void init() {
    Main.main(new String[] {});
    app = Main.mainInstance;
  }

  @Test
  void runEnvironment() throws IOException, InterruptedException {
    ChargedParticlePlugin cpPlugin = new ChargedParticlePlugin();
    cpPlugin.registerBehavior(app.getWorld().getBehaviorManager());

    VelocityPlugin vPlugin = new VelocityPlugin();
    vPlugin.registerBehavior(app.getWorld().getBehaviorManager());

    initObjects(app.getWorld().getWorldContext().getObjectContext());
    Thread.currentThread().join();
  }

  private void initObjects(ObjectWorldContext objectContext) {
    EObject fixedParticle1 = generateFixedParticle("FixedParticle1", new Vector3(10, 0),  1.3e-10);
    EObject fixedParticle2 = generateFixedParticle("FixedParticle2", new Vector3(0, 10), -1.3e-10);
    EObject fixedParticle3 = generateFixedParticle("FixedParticle3", new Vector3(0, 0, 10), -1.3e-10);

    EObject dynamicParticle = generateDynamicParticle("DynamicParticle1", new Vector3(), 1.3e-10, 6.0e-13);

    objectContext.addObject(fixedParticle1);
    objectContext.addObject(fixedParticle2);
    objectContext.addObject(fixedParticle3);
    objectContext.addObject(dynamicParticle);
  }

  private EObject generateFixedParticle(String id, Vector3 position, double charge) {
    EObject result = new EObject(id);
    result.setProperty(new EProperty(ChargedParticleIds.PARTICLE_TYPE_PROPERTY, ChargedParticleIds.PARTICLE_TYPE_PROPERTY, ParticleType.Fixed));
    result.setProperty(new EProperty(ChargedParticleIds.CHARGE_VALUE_PROPERTY, ChargedParticleIds.CHARGE_VALUE_PROPERTY, charge));
    result.setProperty(new EProperty("POSITION", "POSITION", position));

    return result;
  }

  private EObject generateDynamicParticle(String id, Vector3 position, double charge, double mass) {
    EObject result = new EObject(id);
    result.setProperty(new EProperty(ChargedParticleIds.PARTICLE_TYPE_PROPERTY, ChargedParticleIds.PARTICLE_TYPE_PROPERTY, ParticleType.Dynamic));
    result.setProperty(new EProperty(ChargedParticleIds.CHARGE_VALUE_PROPERTY, ChargedParticleIds.CHARGE_VALUE_PROPERTY, charge));
    result.setProperty(new EProperty("POSITION", "POSITION", position));
    result.setProperty(new EProperty("MASS", "MASS", mass));

    return result;
  }
}
