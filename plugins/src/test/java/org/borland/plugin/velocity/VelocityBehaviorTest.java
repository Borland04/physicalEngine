package org.borland.plugin.velocity;


import org.borland.core.model.behavior.BehaviorContext;
import org.borland.core.model.object.EObject;
import org.borland.core.model.property.EProperty;
import org.borland.core.util.Vector3;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class VelocityBehaviorTest {

    private static final String POSITION_ID = "POSITION";
    private static final String VELOCITY_ID = "VELOCITY";

    private VelocityBehavior velocityBehavior;

    @BeforeClass
    public void init() {
        velocityBehavior = new VelocityBehavior();
    }

    @Test
    public void allPresent_updatePosition() {
        EObject obj = new EObject("obj1");
        obj.setProperty(new EProperty(POSITION_ID, "Position", new Vector3(1, 2, 3)));
        obj.setProperty(new EProperty(VELOCITY_ID, "Velocity", new Vector3(3, 2, 1)));

        BehaviorContext ctx = new BehaviorContext(obj, new LinkedList<>());
        velocityBehavior.behave(ctx, 1);

        Optional<EProperty> posMaybe = obj.getProperty(POSITION_ID);
        Assert.assertTrue(posMaybe.isPresent());

        Vector3 pos = posMaybe.get().getValue(Vector3.class);
        Assert.assertEquals(pos.getX(), 4);
        Assert.assertEquals(pos.getY(), 4);
        Assert.assertEquals(pos.getZ(), 4);
    }

    @Test
    public void missedPosition_doNothing() {
        EObject obj = new EObject("obj1");
        obj.setProperty(new EProperty(VELOCITY_ID, "Velocity", new Vector3(3, 2, 1)));

        BehaviorContext ctx = new BehaviorContext(obj, new LinkedList<>());
        velocityBehavior.behave(ctx, 1);

        Optional<EProperty> posMaybe = obj.getProperty(POSITION_ID);
        Assert.assertTrue(!posMaybe.isPresent());
    }

    @Test
    public void missedVelocity_doNothing() {
        EObject obj = new EObject("obj1");
        obj.setProperty(new EProperty(POSITION_ID, "Position", new Vector3(1, 2, 3)));

        BehaviorContext ctx = new BehaviorContext(obj, new LinkedList<>());
        velocityBehavior.behave(ctx, 1);

        Optional<EProperty> posMaybe = obj.getProperty(POSITION_ID);
        Assert.assertTrue(posMaybe.isPresent());

        Vector3 pos = posMaybe.get().getValue(Vector3.class);
        Assert.assertEquals(pos.getX(), 1);
        Assert.assertEquals(pos.getY(), 2);
        Assert.assertEquals(pos.getZ(), 3);
    }

    @Test
    public void allPresent_respectDeltaTime() {
        EObject obj = new EObject("obj1");
        obj.setProperty(new EProperty(POSITION_ID, "Position", new Vector3(1, 2, 3)));
        obj.setProperty(new EProperty(VELOCITY_ID, "Velocity", new Vector3(3, 2, 1)));

        BehaviorContext ctx = new BehaviorContext(obj, new LinkedList<>());
        velocityBehavior.behave(ctx, 0.1);

        Optional<EProperty> posMaybe = obj.getProperty(POSITION_ID);
        Assert.assertTrue(posMaybe.isPresent());

        Vector3 pos = posMaybe.get().getValue(Vector3.class);
        Assert.assertEquals(pos.getX(), 1.3);
        Assert.assertEquals(pos.getY(), 2.2);
        Assert.assertEquals(pos.getZ(), 3.1);
    }

}
