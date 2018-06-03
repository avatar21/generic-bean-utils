package com.github.avatar21.generics.model;

/**
 * <p>thru combination with to filter collection, implementing apply() method, to return a true(or false) to determine filtering criteria(s)</p>
 *
 * @param <T> bean type containing filtering criteria(s)
 */
public interface IPredicate<T> {
    boolean apply(T type);
}
