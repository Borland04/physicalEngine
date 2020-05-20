package org.borland.core.util;

import io.reactivex.rxjava3.functions.Consumer;

public interface Observable<T> {
    void subscribe(Consumer<T> consumer);
}
