package com.github.avatar21.generics.handler;

import java.lang.reflect.InvocationTargetException;

/**
 * parsable parent (holder of child)/ child (detail) handler
 *
 * @param <P> parent type
 * @param <C> child type
 */
public interface IParsablePredicateHandler<P, C> {
    P parseChildByType(P target, C source) throws InvocationTargetException, IllegalAccessException;
}
