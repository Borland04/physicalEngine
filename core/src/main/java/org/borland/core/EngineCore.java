package org.borland.core;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import org.borland.core.model.behavior.BehaviorContext;
import org.borland.core.model.behavior.EBehavior;
import org.borland.core.model.object.EObject;
import org.borland.core.model.plugin.BehaviorManager;
import org.borland.core.model.worldcontext.WorldContext;
import org.borland.core.util.Tuple;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EngineCore {

    private WorldContext worldContext = new WorldContext();
    private BehaviorManager behaviorManager = new BehaviorManagerImpl(worldContext);

    public void tick(double deltaTime) {
        Iterator<Tuple<EObject, List<EObject>>> objectsIterator =
                worldContext.getObjectContext().getObjectsIterator();

        while(objectsIterator.hasNext()) {
            Tuple<EObject, List<EObject>> currentIteration = objectsIterator.next();
            handleObject(currentIteration, deltaTime);
        }
    }

    public void handleObject(Tuple<EObject, List<EObject>> objContext, double deltaTime) {
        BehaviorContext currentBehaviorContext = new BehaviorContext(objContext.fst, objContext.snd);

        List<EBehavior> behaviorList = worldContext.getBehaviorContext().getBehaviorList();
        for(EBehavior behavior: behaviorList) {
            behavior.behave(currentBehaviorContext, deltaTime);
        }
    }

    public BehaviorManager getBehaviorManager() {
        return behaviorManager;
    }

    public WorldContext getWorldContext() {
        return worldContext;
    }

}
