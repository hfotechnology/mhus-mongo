package dev.morphia.query;


/**
 * The results of a query.  These results aren't materialized until a method on this interface is called.
 *
 * @param <T>
 *  use {@link Query} instead
 */

public interface QueryResults<T> extends Iterable<T> {

}
