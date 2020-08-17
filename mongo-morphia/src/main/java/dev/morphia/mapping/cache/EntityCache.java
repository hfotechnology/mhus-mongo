/**
 * Copyright (C) 2020 Mike Hummel (mh@mhus.de)
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
package dev.morphia.mapping.cache;

import dev.morphia.Key;

/**
 * A primarily internal class used by MorphiaIterator to track entities loaded from mongo to prevent
 * multiple loads of objects when keys are seen multiple times in a query result.
 */
public interface EntityCache {
    /**
     * Looks for a Key in the cache
     *
     * @param k the Key to search for
     * @return true if the Key is found
     */
    Boolean exists(Key<?> k);

    /** Clears the cache */
    void flush();

    /**
     * Returns the entity for a Key
     *
     * @param k the Key to search for
     * @param <T> the type of the entity
     * @return the entity
     */
    <T> T getEntity(Key<T> k);

    /**
     * Returns a proxy for the entity for a Key
     *
     * @param k the Key to search for
     * @param <T> the type of the entity
     * @return the proxy
     */
    <T> T getProxy(Key<T> k);

    /**
     * Notifies the cache of the existence of a Key
     *
     * @param k the Key
     * @param exists true if the Key represents an existing entity
     */
    void notifyExists(Key<?> k, boolean exists);

    /**
     * Adds an entity to the cache
     *
     * @param k the Key of the entity
     * @param t the entity
     * @param <T> the type of the entity
     */
    <T> void putEntity(Key<T> k, T t);

    /**
     * Adds a proxy to the cache
     *
     * @param k the Key of the entity
     * @param t the proxy
     * @param <T> the type of the entity
     */
    <T> void putProxy(Key<T> k, T t);

    /** @return the stats for this cache */
    EntityCacheStatistics stats();
}
