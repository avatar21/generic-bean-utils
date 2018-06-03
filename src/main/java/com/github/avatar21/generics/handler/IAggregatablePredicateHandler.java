package com.github.avatar21.generics.handler;

/**
 * <p>
 *     aggregate predicate handler (travels thru childs and aggregate any attribute(s) of it, setting back the result in parent's attribute(s))
 * </p>
 *
 * @param <P> parent type
 * @param <C> child type
 */
public interface IAggregatablePredicateHandler<P, C> {
    void aggregateByType(P target, C source);
}
