/**
 * Copyright (c) 2008-2015 MongoDB, Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.morphia.query;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import dev.morphia.Datastore;

/** A factory for {@link Query}ies. */
public interface QueryFactory {

    /**
     * Creates and returns a {@link Query} for the given arguments. Default implementations of this
     * method will simply delegate to {@link #createQuery(Datastore, DBCollection, Class, DBObject)}
     * with the last argument being {@code null}.
     *
     * @param datastore the Datastore to use
     * @param collection the collection to query
     * @param type the type of the result
     * @param <T> the type of the result
     * @return the query
     * @see #createQuery(Datastore, DBCollection, Class, DBObject)
     */
    <T> Query<T> createQuery(Datastore datastore, DBCollection collection, Class<T> type);

    /**
     * Creates and returns a {@link Query} for the given arguments. The last argument is optional
     * and may be {@code null}.
     *
     * @param datastore the Datastore to use
     * @param collection the collection to query
     * @param type the type of the result
     * @param query the DBObject containing the query structure
     * @param <T> the type of the result
     * @return the query
     */
    <T> Query<T> createQuery(
            Datastore datastore, DBCollection collection, Class<T> type, DBObject query);

    /**
     * Creates an unvalidated {@link Query} typically for use in aggregation pipelines.
     *
     * @param datastore the Datastore to use
     * @param <T> the type of the result
     * @return the query
     */
    <T> Query<T> createQuery(Datastore datastore);
}
