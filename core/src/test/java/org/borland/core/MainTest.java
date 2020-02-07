package org.borland.core;

import org.borland.core.model.behavior.BehaviorContext;
import org.borland.core.model.behavior.EBehavior;
import org.borland.core.model.object.EObject;
import org.borland.core.model.object.impl.MaterialBody;
import org.borland.core.model.property.EProperty;
import org.borland.core.model.property.impl.IntProperty;
import org.borland.core.model.worldcontext.BehaviorWorldContext;
import org.borland.core.model.worldcontext.ObjectWorldContext;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

public class MainTest {

    Main main;

    @BeforeClass
    public void initWorld() {
        main = new Main();

        initObjects(main.getWorldContext().getObjectContext());
        initBehaviors(main.getWorldContext().getBehaviorContext());
    }

    private void initObjects(ObjectWorldContext ctx) {
        EObject body1 = new MaterialBody("body1");
        EObject body2 = new MaterialBody("body2");
        EObject body3 = new MaterialBody("body3");

        ctx.addObject(body1);
        ctx.addObject(body2);
        ctx.addObject(body3);
    }

    private void initBehaviors(BehaviorWorldContext ctx) {
        EBehavior behavior1 = new EBehavior() {
            @Override
            public @NotNull String getId() {
                return "behavior1";
            }

            @Override
            public @NotNull String getLabel() {
                return "Behavior 1";
            }

            @Override
            public void behave(@NotNull BehaviorContext context, long deltaTime) {
                EObject obj = context.getObject();
                obj.getProperty("prop1").ifPresent(prop -> {
                    Integer value = prop.getValue(Integer.class);
                    prop.setValue(value + 1);
                });
            }
        };

        ctx.addBehavior(behavior1);
    }


    @BeforeMethod
    public void resetWorld() {
        ObjectWorldContext ctx = main.getWorldContext().getObjectContext();

        Optional<EObject> obj1 = ctx.getObject("body1");
        obj1.ifPresent(o -> o.setProperty(new IntProperty("prop1", "prop 1", 0)));

        Optional<EObject> obj2 = ctx.getObject("body2");
        obj2.ifPresent(o -> o.setProperty(new IntProperty("prop1", "prop 1", 0)));
    }


    @Test
    public void tick_shouldApplyBehavior() {
        main.tick(1);

        Optional<EProperty> body1Prop = main.getWorldContext().getObjectContext().getObject("body1")
                .flatMap(body -> body.getProperty("prop1"));

        Optional<EProperty> body2Prop = main.getWorldContext().getObjectContext().getObject("body2")
                .flatMap(body -> body.getProperty("prop1"));

        Optional<EProperty> body3Prop = main.getWorldContext().getObjectContext().getObject("body3")
                .flatMap(body -> body.getProperty("prop1"));

        Assert.assertTrue(body1Prop.isPresent());
        Assert.assertEquals((int)body1Prop.get().getValue(Integer.class), 1);

        Assert.assertTrue(body2Prop.isPresent());
        Assert.assertEquals((int)body2Prop.get().getValue(Integer.class), 1);

        Assert.assertFalse(body3Prop.isPresent());
    }

}
