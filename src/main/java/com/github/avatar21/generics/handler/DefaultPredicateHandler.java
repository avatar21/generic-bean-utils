package com.github.avatar21.generics.handler;

import java.lang.reflect.InvocationTargetException;

/**
 * default predicate handler(s) (do not implement any method)
 */
public class DefaultPredicateHandler<P, C> implements IParsablePredicateHandler<P, C>, IAggregatablePredicateHandler<P, C> {

    @Override
    public P parseChildByType(P target, C source) throws InvocationTargetException, IllegalAccessException {
        return target;
    }

    @Override
    public void aggregateByType(P target, C source) {
        // do nothing
    }
}
