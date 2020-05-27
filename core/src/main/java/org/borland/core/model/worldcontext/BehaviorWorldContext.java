package org.borland.core.model.worldcontext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.borland.core.model.behavior.EBehavior;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BehaviorWorldContext {

    private static final Logger logger = LogManager.getLogger(BehaviorWorldContext.class);

    public List<EBehavior>  behaviorList = new LinkedList<>();

    /**
     * Add behavior to a context
     * @param behavior behavior to add
     * @throws IllegalArgumentException when behavior with the same ID already exists
     */
    public void addBehavior(@NotNull EBehavior behavior) {
        String newBehaviorId = behavior.getId();
        if(getBehavior(newBehaviorId).isPresent()) {
            logger.warn("Trying to add behavior with id '{}'," +
                    "but behavior with the same id already exists", newBehaviorId);
            throw new IllegalArgumentException(
                    String.format("Behavior with id '%s' already exists", newBehaviorId));
        }

        behaviorList.add(behavior);
        logger.debug("Behavior with id '{}' successfully added", newBehaviorId);
    }

    public Optional<EBehavior> getBehavior(@NotNull String id) {
        logger.trace("Trying to get behavior with id '{}'", id);
        return behaviorList.stream()
            .filter(b -> id.equals(b.getId()))
            .findAny();
    }

    @NotNull
    public List<EBehavior> getBehaviorList() {
        return behaviorList;
    }

}
