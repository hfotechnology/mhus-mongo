/**
 * Copyright (C) 2019 Mike Hummel (mh@mhus.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.morphia.query;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bson.types.CodeWScope;

import com.mongodb.Bytes;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoIterable;

import dev.morphia.Key;
import dev.morphia.query.internal.MorphiaCursor;
import dev.morphia.query.internal.MorphiaKeyCursor;

/** @param <T> The java type to query against */
public interface Query<T> extends QueryResults<T>, MongoIterable<T> {
    /**
     * Creates a container to hold 'and' clauses
     *
     * @param criteria the clauses to 'and' together
     * @return the container
     */
    CriteriaContainer and(Criteria... criteria);

    /**
     * Batch-size of the fetched result (cursor).
     *
     * @param value must be >= 0. A value of 0 indicates the server default.
     * @return this use the methods that accept Options directly
     * @see FindOptions#batchSize(int)
     */
    Query<T> batchSize(int value);

    /**
     * Creates and returns a copy of this {@link Query}.
     *
     * @return this
     * @morphia.internal
     */
    Query<T> cloneQuery();

    /**
     * This makes it possible to attach a comment to a query. Because these comments propagate to
     * the profile log, adding comments can make your profile data much easier to interpret and
     * trace.
     *
     * @param comment the comment to add
     * @return the Query to enable chaining of commands
     * @see FindOptions#modifier(String, Object) use the methods that accept Options directly. This
     *     can be replicated with {@code options.modifier("$comment", comment)}
     * @mongodb.driver.manual reference/operator/meta/comment $comment
     */
    Query<T> comment(String comment);

    /**
     * Creates a criteria to apply against a field
     *
     * @param field the field
     * @return the FieldEnd to define the criteria
     */
    FieldEnd<? extends CriteriaContainer> criteria(String field);

    /**
     * Disables cursor timeout on server.
     *
     * @return this use the methods that accept Options directly
     * @see FindOptions#noCursorTimeout(boolean)
     */
    Query<T> disableCursorTimeout();

    /**
     * Disable snapshotted mode (default mode). This will be faster but changes made during the
     * cursor may cause duplicates.
     *
     * @return this use the methods that accept Options directly. This can be replicated using
     *     {@code options.modifier("$snapshot", false)}
     * @see FindOptions#modifier(String, Object)
     */
    Query<T> disableSnapshotMode();

    /**
     * Turns off validation (for all calls made after)
     *
     * @return this
     */
    Query<T> disableValidation();

    /**
     * Enables cursor timeout on server.
     *
     * @return this use the methods that accept Options directly
     * @see FindOptions#noCursorTimeout(boolean)
     */
    Query<T> enableCursorTimeout();

    /**
     * Enables snapshotted mode where duplicate results (which may be updated during the lifetime of
     * the cursor) will not be returned. Not compatible with order/sort and hint.
     *
     * @return this use the methods that accept Options directly. This can be replicated using
     *     {@code options.modifier("$snapshot", true)}
     * @see FindOptions#modifier(String, Object)
     */
    Query<T> enableSnapshotMode();

    /**
     * Turns on validation (for all calls made after); by default validation is on
     *
     * @return this
     */
    Query<T> enableValidation();

    /**
     * Provides information on the query plan. The query plan is the plan the server uses to find
     * the matches for a query. This information may be useful when optimizing a query.
     *
     * @return Map describing the process used to return the query results.
     * @mongodb.driver.manual reference/operator/meta/explain/ explain
     */
    Map<String, Object> explain();

    /**
     * Provides information on the query plan. The query plan is the plan the server uses to find
     * the matches for a query. This information may be useful when optimizing a query.
     *
     * @param options the options to apply to the explain operation
     * @return Map describing the process used to return the query results.
     * @mongodb.driver.manual reference/operator/meta/explain/ explain
     * @since 1.3
     */
    Map<String, Object> explain(FindOptions options);

    /**
     * Fluent query interface: {@code createQuery(Ent.class).field("count").greaterThan(7)...}
     *
     * @param field the field
     * @return the FieldEnd to define the criteria
     */
    FieldEnd<? extends Query<T>> field(String field);

    /**
     * Create a filter based on the specified condition and value.
     *
     * <p><b>Note</b>: Property is in the form of "name op" ("age >").
     *
     * <p>
     *
     * <p>Valid operators are ["=", "==","!=", "<>", ">", "<", ">=", "<=", "in", "nin", "all",
     * "size", "exists"]
     *
     * <p>
     *
     * <p>Examples:
     *
     * <p>
     *
     * <ul>
     *   <li>{@code filter("yearsOfOperation >", 5)}
     *   <li>{@code filter("rooms.maxBeds >=", 2)}
     *   <li>{@code filter("rooms.bathrooms exists", 1)}
     *   <li>{@code filter("stars in", new Long[]{3, 4}) //3 and 4 stars (midrange?)}
     *   <li>{@code filter("quantity mod", new Long[]{4, 0}) // customers ordered in packs of 4)}
     *   <li>{@code filter("age >=", age)}
     *   <li>{@code filter("age =", age)}
     *   <li>{@code filter("age", age)} (if no operator, = is assumed)
     *   <li>{@code filter("age !=", age)}
     *   <li>{@code filter("age in", ageList)}
     *   <li>{@code filter("customers.loyaltyYears in", yearsList)}
     * </ul>
     *
     * <p>
     *
     * <p>You can filter on id properties <strong>if</strong> this query is restricted to a
     * Class<T>.
     *
     * @param condition the condition to apply
     * @param value the value to apply against
     * @return this
     */
    Query<T> filter(String condition, Object value);

    /**
     * @return the batch size
     * @see #batchSize(int) use the methods that accept Options directly
     * @see FindOptions#batchSize(int)
     */
    int getBatchSize();

    /**
     * @return the collection this query targets
     *     <p>This is an internal method and subject to change or removal. Do not use.
     * @morphia.internal
     */
    DBCollection getCollection();

    /**
     * @return the entity {@link Class}.
     * @morphia.internal
     */
    Class<T> getEntityClass();

    /**
     * @return the Mongo fields {@link DBObject}.
     * @morphia.internal
     */
    DBObject getFieldsObject();

    /**
     * @return the limit
     * @see #limit(int) use the methods that accept Options directly
     * @see FindOptions#limit(int)
     */
    int getLimit();

    /**
     * @return the offset.
     * @see #offset(int) use the methods that accept Options directly
     * @see FindOptions#getSkip()
     */
    int getOffset();

    /**
     * @return the Mongo query {@link DBObject}.
     * @morphia.internal
     */
    DBObject getQueryObject();

    /**
     * @return the Mongo sort {@link DBObject}.
     * @morphia.internal
     */
    DBObject getSortObject();

    /**
     * Hints as to which index should be used.
     *
     * @param idxName the index name to hint
     * @return this use the methods that accept Options directly. This can be replicated with {@code
     *     options.modifier("$hint", idxName)}
     * @see FindOptions#modifier(String, Object)
     */
    Query<T> hintIndex(String idxName);

    /**
     * Limit the fetched result set to a certain number of values.
     *
     * @param value must be >= 0. A value of 0 indicates no limit. For values < 0, use {@link
     *     FindOptions#batchSize(int)} which is the preferred method
     * @return this use the methods that accept Options directly
     * @see FindOptions#limit(int)
     */
    Query<T> limit(int value);

    /**
     * Specify the inclusive lower bound for a specific index in order to constrain the results of
     * this query.
     *
     * <p>You can chain key/value pairs to build a constraint for a compound index. For instance:
     *
     * <p>{@code query.lowerIndexBound(new BasicDBObject("a", 1).append("b", 2)); }
     *
     * <p>to build a constraint on index {@code {"a", "b"}}
     *
     * @param lowerBound The inclusive lower bound.
     * @return this
     * @mongodb.driver.manual reference/operator/meta/min/ $min use the methods that accept Options
     *     directly. This can be replicated using {@code options.modifier("$min", new Document(...))
     *     }
     * @see FindOptions#modifier(String, Object)
     */
    Query<T> lowerIndexBound(DBObject lowerBound);

    /**
     * Constrains the query to only scan the specified number of documents when fulfilling the
     * query.
     *
     * @param value must be > 0. A value < 0 indicates no limit
     * @return this
     * @mongodb.driver.manual reference/operator/meta/maxScan/#op._S_maxScan $maxScan use the
     *     methods that accept Options directly. This can be replicated using {@code
     *     options.modifier("$maxScan", value) }
     * @see FindOptions#modifier(String, Object)
     */
    Query<T> maxScan(int value);

    /**
     * Specifies a time limit for executing the query. Requires server version 2.6 or above.
     *
     * @param maxTime must be > 0. A value < 0 indicates no limit
     * @param maxTimeUnit the unit of time to use
     * @return this use the methods that accept Options directly. This can be replicated using
     *     {@code options.maxTime(value, unit) }
     * @see FindOptions#modifier(String, Object)
     */
    Query<T> maxTime(long maxTime, TimeUnit maxTimeUnit);

    /**
     * Starts the query results at a particular zero-based offset.
     *
     * @param value must be >= 0
     * @return this use the methods that accept Options directly
     * @see FindOptions#skip(int)
     */
    Query<T> offset(int value);

    /**
     * Creates a container to hold 'or' clauses
     *
     * @param criteria the clauses to 'or' together
     * @return the container
     */
    CriteriaContainer or(Criteria... criteria);

    /**
     * Sorts based on a property (defines return order). Examples:
     *
     * <p>
     *
     * <ul>
     *   <li>{@code order("age")}
     *   <li>{@code order("-age")} (descending order)
     *   <li>{@code order("age, date")}
     *   <li>{@code order("age,-date")} (age ascending, date descending)
     * </ul>
     *
     * @param sort the sort order to apply
     * @return this use {@link #order(Sort...)}
     */
    Query<T> order(String sort);

    /**
     * Sorts based on a metadata (defines return order). Example: {@code order(Meta.textScore())}
     * ({textScore : { $meta: "textScore" }})
     *
     * @param sort the sort order to apply
     * @return this
     */
    Query<T> order(Meta sort);

    /**
     * Sorts based on a specified sort keys (defines return order).
     *
     * @param sorts the sort order to apply
     * @return this
     */
    Query<T> order(Sort... sorts);

    /**
     * Adds a field to the projection clause. Passing true for include will include the field in the
     * results. Projected fields must all be inclusions or exclusions. You can not include and
     * exclude fields at the same time with the exception of the _id field. The _id field is always
     * included unless explicitly suppressed.
     *
     * @param field the field to project
     * @param include true to include the field in the results
     * @return this
     * @see <a
     *     href="https://docs.mongodb.com/manual/tutorial/project-fields-from-query-results/">Project
     *     Fields to Return from Query</a>
     */
    Query<T> project(String field, boolean include);

    /**
     * Adds an sliced array field to a projection.
     *
     * @param field the field to project
     * @param slice the options for projecting an array field
     * @return this
     * @see <a
     *     href="https://docs.mongodb.com/manual/tutorial/project-fields-from-query-results/">Project
     *     Fields to Return from Query</a>
     * @mongodb.driver.manual /reference/operator/projection/slice/ $slice
     */
    Query<T> project(String field, ArraySlice slice);

    /**
     * Adds a metadata field to a projection.
     *
     * @param meta the metadata option for projecting
     * @return this
     * @see <a
     *     href="https://docs.mongodb.com/manual/tutorial/project-fields-from-query-results/">Project
     *     Fields to Return from Query</a>
     * @mongodb.driver.manual reference/operator/projection/meta/ $meta
     */
    Query<T> project(Meta meta);

    /**
     * Route query to non-primary node
     *
     * @return this
     * @see ReadPreference#secondary()
     * @see ReadPreference#secondaryPreferred() use the methods that accept Options directly
     * @see FindOptions#readPreference(ReadPreference)
     * @see ReadPreference#secondary()
     * @see ReadPreference#secondaryPreferred()
     */
    Query<T> queryNonPrimary();

    /**
     * Route query to primary node
     *
     * @return this
     * @see ReadPreference#primary() use the methods that accept Options directly.
     * @see FindOptions#readPreference(ReadPreference)
     * @see ReadPreference#primary()
     * @see ReadPreference#primaryPreferred()
     */
    Query<T> queryPrimaryOnly();

    /**
     * Limits the fields retrieved to those of the query type -- dangerous with interfaces and
     * abstract classes
     *
     * @return this
     */
    Query<T> retrieveKnownFields();

    /**
     * Limits the fields retrieved
     *
     * @param include true if the fields should be included in the results. false to exclude them.
     * @param fields the fields in question
     * @return this use {@link #project(String, boolean)} instead
     */
    Query<T> retrievedFields(boolean include, String... fields);

    /**
     * Only return the index field or fields for the results of the query. If $returnKey is set to
     * true and the query does not use an index to perform the read operation, the returned
     * documents will not contain any fields
     *
     * @return the Query to enable chaining of commands
     * @mongodb.driver.manual reference/operator/meta/returnKey/#op._S_returnKey $returnKey use the
     *     methods that accept Options directly. This can be replicated using {@code
     *     options.modifier("$returnKey", true) }
     * @see FindOptions#modifier(String, Object)
     */
    Query<T> returnKey();

    /**
     * Perform a text search on the content of the fields indexed with a text index..
     *
     * @param text the text to search for
     * @return the Query to enable chaining of commands
     * @mongodb.driver.manual reference/operator/query/text/ $text
     */
    Query<T> search(String text);

    /**
     * Perform a text search on the content of the fields indexed with a text index..
     *
     * @param text the text to search for
     * @param language the language to use during the search
     * @return the Query to enable chaining of commands
     * @mongodb.driver.manual reference/operator/query/text/ $text
     */
    Query<T> search(String text, String language);

    /**
     * Specify the exclusive upper bound for a specific index in order to constrain the results of
     * this query.
     *
     * <p>You can chain key/value pairs to build a constraint for a compound index. For instance:
     *
     * <p>{@code query.upperIndexBound(new BasicDBObject("a", 1).append("b", 2)); }
     *
     * <p>to build a constraint on index {@code {"a", "b"}}
     *
     * @param upperBound The exclusive upper bound.
     * @return this
     * @mongodb.driver.manual reference/operator/meta/max/ $max use the methods that accept Options
     *     directly. This can be replicated using {@code options.modifier("$max", new Document(...))
     *     }
     * @see FindOptions#modifier(String, Object)
     */
    Query<T> upperIndexBound(DBObject upperBound);

    /**
     * Updates the ReadPreference to use
     *
     * @param readPref the ReadPreference to use
     * @return this
     * @see ReadPreference use the methods that accept Options directly
     * @see FindOptions#readPreference(ReadPreference)
     */
    Query<T> useReadPreference(ReadPreference readPref);

    /**
     * Limit the query using this javascript block; only one per query
     *
     * @param js the javascript block to apply
     * @return this
     */
    Query<T> where(String js);

    /**
     * Limit the query using this javascript block; only one per query
     *
     * @param js the javascript block to apply
     * @return this
     */
    Query<T> where(CodeWScope js);

    /**
     * Execute the query and get the results (as a {@code List<Key<T>>}) This method is provided as
     * a convenience;
     *
     * @return returns a List of the keys of the documents returned by a query use {@link #keys()}
     */
    List<Key<T>> asKeyList();

    /**
     * Execute the query and get the results (as a {@code List<Key<T>>}) This method is provided as
     * a convenience;
     *
     * @param options the options to apply to the find operation
     * @return returns a List of the keys of the documents returned by a query
     * @since 1.3 use {@link #keys(FindOptions)}
     */
    List<Key<T>> asKeyList(FindOptions options);

    /**
     * Execute the query and get the results (as a {@code MorphiaCursor<Key<T>>})
     *
     * @return the keys of the documents returned by this query
     */
    MorphiaKeyCursor<T> keys();

    /**
     * Execute the query and get the results (as a {@code MorphiaCursor<Key<T>>})
     *
     * @param options the options to apply to the find operation
     * @return the keys of the documents returned by this query
     * @since 1.4
     */
    MorphiaKeyCursor<T> keys(FindOptions options);

    /**
     * Execute the query and get the results.
     *
     * @return returns a List of the documents returned by a query use {@link #find(FindOptions)}
     */
    List<T> asList();

    /**
     * Execute the query and get the results.
     *
     * @param options the options to apply to the find operation
     * @return returns a List of the documents returned by a query
     * @since 1.3 use {@link #find(FindOptions)}
     */
    List<T> asList(FindOptions options);

    /**
     * Count the total number of values in the result, ignoring limit and offset
     *
     * @return the count use {@link #count()} instead
     */
    long countAll();

    /**
     * Count the total number of values in the result, ignoring limit and offset
     *
     * @return the count
     * @since 1.3
     */
    long count();

    /**
     * Count the total number of values in the result, ignoring limit and offset
     *
     * @param options the options to apply to the count operation
     * @return the count
     * @since 1.3
     */
    long count(CountOptions options);

    /**
     * Execute the query and get the results.
     *
     * @return an Iterator of the results use {@link #find(FindOptions)} instead
     */
    MorphiaIterator<T, T> fetch();

    /**
     * Execute the query and get the results.
     *
     * @param options the options to apply to the find operation
     * @return an Iterator of the results
     * @since 1.3 use {@link #find(FindOptions)} instead
     */
    MorphiaIterator<T, T> fetch(FindOptions options);

    /**
     * Execute the query and get the results.
     *
     * <p>*note* the return type of this will change in 2.0.
     *
     * @return a MorphiaCursor
     * @since 1.4
     * @see #find(FindOptions)
     */
    MorphiaCursor<T> find();

    /**
     * Execute the query and get the results.
     *
     * @param options the options to apply to the find operation
     * @return a MorphiaCursor
     * @since 1.4
     */
    MorphiaCursor<T> find(FindOptions options);

    /**
     * Execute the query and get only the ids of the results. This is more efficient than fetching
     * the actual results (transfers less data).
     *
     * @return an Iterator of the empty entities use {@link #keys()} instead
     */
    MorphiaIterator<T, T> fetchEmptyEntities();

    /**
     * Execute the query and get only the ids of the results. This is more efficient than fetching
     * the actual results (transfers less data).
     *
     * @param options the options to apply to the find operation
     * @return an Iterator of the empty entities
     * @since 1.3 use {@link #keys(FindOptions)} instead
     */
    MorphiaIterator<T, T> fetchEmptyEntities(FindOptions options);

    /**
     * Execute the query and get the keys for the objects.
     *
     * @return the Key Iterator
     * @see #fetchEmptyEntities use {@link #keys()}
     */
    MorphiaKeyIterator<T> fetchKeys();

    /**
     * Execute the query and get the keys for the objects.
     *
     * @param options the options to apply to the find operation
     * @return the Key Iterator
     * @since 1.3 use {@link #keys(FindOptions)}
     */
    MorphiaKeyIterator<T> fetchKeys(FindOptions options);

    /**
     * Gets the first entity in the result set. Obeys the {@link Query} offset value.
     *
     * @param options the options to apply to the find operation
     * @return the only instance in the result, or null if the result set is empty.
     * @since 1.5
     */
    T first(FindOptions options);

    /**
     * Gets the first entity in the result set. Obeys the {@link Query} offset value.
     *
     * @return the only instance in the result, or null if the result set is empty. use {@link
     *     #first()}
     */
    T get();

    /**
     * Gets the first entity in the result set. Obeys the {@link Query} offset value.
     *
     * @param options the options to apply to the find operation
     * @return the only instance in the result, or null if the result set is empty.
     * @since 1.3 use {@link #first(FindOptions)}
     */
    T get(FindOptions options);

    /**
     * Get the key of the first entity in the result set. Obeys the {@link Query} offset value.
     *
     * @return the key of the first instance in the result, or null if the result set is empty. use
     *     {@link #first()} instead
     */
    Key<T> getKey();

    /**
     * Get the key of the first entity in the result set. Obeys the {@link Query} offset value.
     *
     * @param options the options to apply to the find operation
     * @return the key of the first instance in the result, or null if the result set is empty.
     * @since 1.3 use {@link #first()} instead
     */
    Key<T> getKey(FindOptions options);

    /**
     * Calls {@code tail(true);}
     *
     * @return an Iterator.
     * @see #tail(boolean) set the CursorType on {@link FindOptions} and use {@link
     *     #find(FindOptions)} instead
     */
    MorphiaIterator<T, T> tail();

    /**
     * Returns an tailing iterator over a set of elements of type T. If awaitData is true, this
     * iterator blocks on hasNext() until new data is avail (or some amount of time has passed).
     * Note that if no data is available at all, hasNext() might return immediately. You should wrap
     * tail calls in a loop if you want this to be blocking.
     *
     * @param awaitData passes the awaitData to the cursor
     * @return an Iterator.
     * @see Bytes#QUERYOPTION_AWAITDATA set the CursorType on {@link FindOptions} and use {@link
     *     #find(FindOptions)} instead. This can be replicated using {@code findOptions.cursorType
     *     (awaitData ? TailableAwait : Tailable)}
     */
    MorphiaIterator<T, T> tail(boolean awaitData);
}
