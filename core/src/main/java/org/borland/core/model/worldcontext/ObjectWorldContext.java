package org.borland.core.model.worldcontext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.borland.core.model.object.EObject;
import org.borland.core.util.Tuple;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

// TODO: what if objects will be changed while iterator works(Passed 'objects' by reference)
public class ObjectWorldContext {

    private static Logger logger = LogManager.getLogger(ObjectWorldContext.class);

    private List<EObject> objects = new LinkedList<>();

    /**
     *
     * @param obj
     */
    // TODO: javaDoc
    public void addObject(@NotNull EObject obj) {
        String objectId = obj.getId();
        logger.trace("Trying to add object with id '{}' to context", objectId);

        if(getObject(objectId).isPresent()) {
            logger.warn("Object with id '{}' already exists in context", objectId);
            throw new IllegalArgumentException("Property with the same id already exists");
        }

        objects.add(obj);
        logger.debug("Successfully added object with id '{}' to context", objectId);
    }


    @NotNull
    public Optional<EObject> getObject(@NotNull String id) {
        return objects.stream()
                .filter(o -> id.equals(o.getId()))
                .findFirst();
    }

    @NotNull
    public List<EObject> getObjects() {
        return objects;
    }

    // TODO: JavaDoc
    public Iterator<Tuple<EObject, List<EObject>>> getObjectsIterator() {
        return new ObjectWorldContextIterator(this.objects);
    }

    public void removeObject(@NotNull String id) {
        Optional<EObject> maybeObj = getObject(id);

        if(maybeObj.isEmpty()) {
            logger.warn("Object with id '{}' does not exists in context. Skip removing", id);
        }
        else {
            removeObject(maybeObj.get());
        }
    }

    public void removeObject(@NotNull EObject obj) {
        logger.trace("Trying to delete object: {}", obj);
        objects.remove(obj);
    }

}

class ObjectWorldContextIterator implements Iterator<Tuple<EObject, List<EObject>>> {

    private List<EObject> objects;
    private Iterator<EObject> originalIterator;

    public ObjectWorldContextIterator(List<EObject> objects) {
        this.objects = objects;
        originalIterator = this.objects.iterator();
    }


    @Override
    public boolean hasNext() {
        return originalIterator.hasNext();
    }

    @Override
    public Tuple<EObject, List<EObject>> next() {
        EObject currentObject = originalIterator.next();
        List<EObject> others = objects.stream()
                .filter(o -> o != currentObject)
                .collect(Collectors.toList());

        return new Tuple<>(currentObject, others);
    }

}