package org.borland.core.model.worldcontext;

public class WorldContext {

    private BehaviorWorldContext behaviorContext;
    private ObjectWorldContext objectContext;

    {
        behaviorContext = new BehaviorWorldContext();
        objectContext = new ObjectWorldContext();
    }

    public BehaviorWorldContext getBehaviorContext() {
        return behaviorContext;
    }

    public ObjectWorldContext getObjectContext() {
        return objectContext;
    }
}
