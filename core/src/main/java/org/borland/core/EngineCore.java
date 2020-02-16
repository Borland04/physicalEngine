package org.borland.core;

import org.borland.core.model.behavior.BehaviorContext;
import org.borland.core.model.behavior.EBehavior;
import org.borland.core.model.object.EObject;
import org.borland.core.model.worldcontext.WorldContext;
import org.borland.core.util.Tuple;

import java.util.Iterator;
import java.util.List;

public class EngineCore {

    private WorldContext worldContext = new WorldContext();

    public void tick(long deltaTime) {
        var objectsIterator = worldContext.getObjectContext().getObjectsIterator();
        while(objectsIterator.hasNext()) {
            var currentIteration = objectsIterator.next();
            handleObject(currentIteration, deltaTime);
        }
    }

    public void handleObject(Tuple<EObject, List<EObject>> objContext, long deltaTime) {
        BehaviorContext currentBehaviorContext = new BehaviorContext(objContext.fst, objContext.snd);

        List<EBehavior> behaviorList = worldContext.getBehaviorContext().getBehaviorList();
        for(EBehavior behavior: behaviorList) {
            behavior.behave(currentBehaviorContext, deltaTime);
        }
    }

    public WorldContext getWorldContext() {
        return worldContext;
    }
}
