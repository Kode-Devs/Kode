package org.kodedevs.cdi;

import java.lang.reflect.InvocationTargetException;

public final class Depends {

    // It should not have any instance of itself
    private Depends() {}

    public static <T> T on(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }
}
