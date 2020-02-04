package org.borland.core.model;

import org.borland.core.model.object.EObject;

import java.util.List;

public interface EContext {
    EObject getObject(String instanceName);
    List<EObject> getObjects();
    List<EObject> getOtherObjects(EObject obj);
}
