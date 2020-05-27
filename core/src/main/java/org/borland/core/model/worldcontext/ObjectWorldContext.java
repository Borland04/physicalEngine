package org.borland.core.model.worldcontext;

import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.borland.core.model.object.EObject;
import org.borland.core.util.Observable;
import org.borland.core.util.Tuple;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: what if objects will be changed while iterator works(Passed 'objects' by reference)
public class ObjectWorldContext implements Observable<ObjectWorldContext> {

    private static Logger logger = LogManager.getLogger(ObjectWorldContext.class);
    private final Subject subject = PublishSubject.create();

    private List<EObject> objects = new LinkedList<>();

    /**
     * Add an object into the world
     * @param obj Object to add
     * @throws IllegalArgumentException if object with the same ID already exists
     */
    public void addObject(@NotNull EObject obj) {
        String objectId = obj.getId();
        logger.trace("Trying to add object with id '{}' to context", objectId);

        if(getObject(objectId).isPresent()) {
            logger.warn("Object with id '{}' already exists in context", objectId);
            throw new IllegalArgumentException(
                    String.format("Object with the same id(%s) already exists", objectId));
        }

        objects.add(obj);
        logger.debug("Successfully added object with id '{}' to context", objectId);

        onChanged();
    }


    @Override
    public void subscribe(Consumer<ObjectWorldContext> consumer) {
        subject.subscribe(consumer);
    }

    private void onChanged() {
        logger.debug("Object world context was changed. Emit 'onNext' for all observers");
        subject.onNext(this);
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

        if(!maybeObj.isPresent()) {
            logger.warn("Object with id '{}' does not exists in context. Skip removing", id);
        }
        else {
            removeObject(maybeObj.get());
        }
    }

    public void removeObject(@NotNull EObject obj) {
        logger.trace("Trying to delete object: {}", obj);
        objects.remove(obj);
        onChanged();
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
