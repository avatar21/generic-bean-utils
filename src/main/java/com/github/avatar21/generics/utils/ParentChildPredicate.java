package com.github.avatar21.generics.utils;

import com.github.avatar21.generics.handler.DefaultPredicateHandler;
import com.github.avatar21.generics.model.IPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * generic parent child filterable/ parsable/ aggregate-able utility function
 */
public class ParentChildPredicate {
    private static final Logger logger = LoggerFactory.getLogger(ParentChildPredicate.class);

    /**
     * <p>
     *     processor for filtering, parsable and aggregate-able handler based on typed-predicate
     * </p>
     *
     * @param sources collection of child
     * @param target parent
     * @param predicate filtering predicate
     * @param childClass child class
     * @param handler filtering handler
     * @param isParseChild whether to execute parse child handler
     * @param <P> parent type
     * @param <C> child type
     * @param <H> handler
     * @return parent as a result
     */
    public static <P, C, H extends DefaultPredicateHandler> P filter(Collection<C> sources, P target, IPredicate<C> predicate,
            Class<C> childClass, H handler, boolean isParseChild) {
        for (C source : sources) {
            if (predicate.apply(source)) {
                try {
                    if (handler != null) {
                        // handle aggregate function here
                        handler.aggregateByType(target, source);
                        // parse child type here
                        if (isParseChild) {
                            handler.parseChildByType(target, source);
                        }
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    logger.error(e.getLocalizedMessage());
                }
            }
        }
        return target;
    }
}
