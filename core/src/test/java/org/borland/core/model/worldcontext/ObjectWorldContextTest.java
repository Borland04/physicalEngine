package org.borland.core.model.worldcontext;

import org.borland.core.model.object.EObject;
import org.borland.core.util.Tuple;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ObjectWorldContextTest {

    private ObjectWorldContext context;

    @BeforeMethod
    public void initContext() {
        context = new ObjectWorldContext();
        fillContextWithTestObjects(context);
    }

    private void fillContextWithTestObjects(ObjectWorldContext context) {
        EObject obj1 = createObject("id1");
        EObject obj2 = createObject("id2");
        EObject obj3 = createObject("id3");
        EObject obj4 = createObject("id4");

        context.addObject(obj1);
        context.addObject(obj2);
        context.addObject(obj3);
        context.addObject(obj4);
    }

    private EObject createObject(String id) {
        return new EObject(id);
    }

    @Test
    public void addObjectWithNewId_success() {
        EObject newObject = createObject("newId");

        context.addObject(newObject);
        Optional<EObject> addedNewObject = context.getObject("newId");

        Assert.assertTrue(addedNewObject.isPresent());
        Assert.assertEquals(addedNewObject.get(), newObject);
    }

    @Test
    public void addObjectWithExistingId_throwException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            EObject existingObject = createObject("id1");
            context.addObject(existingObject);
        });
    }

    @Test
    public void getObjectWithExistingId_returnsObject() {
        Optional<EObject> existingObject = context.getObject("id1");

        Assert.assertTrue(existingObject.isPresent());
    }

    @Test
    public void getObjectWithNewId_returnsEmptyOptional() {
        Optional<EObject> newObject = context.getObject("newId");

        Assert.assertFalse(newObject.isPresent());
    }

    @Test
    public void removeObjectWithExistingId_success() {
        String id = "id1";
        Assert.assertTrue(context.getObject(id).isPresent());

        context.removeObject(id);

        Assert.assertFalse(context.getObject(id).isPresent());
    }

    @Test
    public void removeObjectWithNewId_doNothing() {
        List<String> expectedIds = context.getObjects().stream()
                .map(EObject::getId)
                .collect(Collectors.toList());

        context.removeObject("newId");

        List<String> actualIds = context.getObjects().stream()
                .map(EObject::getId)
                .collect(Collectors.toList());

        Assert.assertEquals(actualIds, expectedIds);
    }

    @Test
    public void removeExistingObject_success() {
        Optional<EObject> obj1 = context.getObject("id1");

        Assert.assertTrue(obj1.isPresent());
        context.removeObject(obj1.get());

        Assert.assertFalse(context.getObject("id1").isPresent());
    }

    @Test
    public void removeExistingObject_removeOnlyRequiredObject() {
        String idToRemove = "id1";
        List<String> expectedIds = context.getObjects().stream()
                .map(EObject::getId)
                .filter(id -> !idToRemove.equals(id))
                .collect(Collectors.toList());

        context.removeObject(idToRemove);

        List<String> actualIds = context.getObjects().stream()
                .map(EObject::getId)
                .collect(Collectors.toList());

        Assert.assertEquals(actualIds, expectedIds);
    }


    // Iterator testing

    @Test
    public void iterator_iteratesThroughAllItemsOrdered() {
        List<String> expectedIds = context.getObjects().stream()
                .map(EObject::getId)
                .collect(Collectors.toList());

        Iterator<Tuple<EObject, List<EObject>>> iter = context.getObjectsIterator();

        for(String expectedId: expectedIds) {
            Assert.assertTrue(iter.hasNext());

            Tuple<EObject, List<EObject>> current = iter.next();
            Assert.assertEquals(current.fst.getId(), expectedId);
        }
    }

    @Test
    public void iterator_iteratesWithRightOthers() {
        List<String> allIds = context.getObjects().stream()
                .map(EObject::getId)
                .collect(Collectors.toList());

        Iterator<Tuple<EObject, List<EObject>>> iter = context.getObjectsIterator();

        while(iter.hasNext()) {
            Tuple<EObject, List<EObject>> current = iter.next();
            List<String> expectedOthers = allIds.stream()
                    .filter(id -> !id.equals(current.fst.getId()))
                    .collect(Collectors.toList());

            List<String> actualOthers = current.snd.stream()
                    .map(EObject::getId)
                    .collect(Collectors.toList());

            Assert.assertEquals(actualOthers, expectedOthers);
        }
    }

}
