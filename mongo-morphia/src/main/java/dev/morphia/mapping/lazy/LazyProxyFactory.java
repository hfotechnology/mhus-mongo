/**
 * Copyright (c) 2008-2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.morphia.mapping.lazy;


import java.util.Collection;
import java.util.Map;

import dev.morphia.Datastore;
import dev.morphia.Key;


/**
 * @author uwe schaefer
 */
public interface LazyProxyFactory {
    /**
     * Creates a proxy for a List.
     *
     * @param <T>               the type of the entities
     * @param datastore         the Datastore to use when fetching this reference
     * @param listToProxy       the list to proxy
     * @param referenceObjClass the type of the referenced objects
     * @param ignoreMissing     ignore references that don't exist in the database
     * @return the proxy
     */
    <T extends Collection> T createListProxy(final Datastore datastore, T listToProxy, Class referenceObjClass, boolean ignoreMissing);

    /**
     * Creates a proxy for a Map.
     *
     * @param <T>               the type of the entities
     * @param datastore         the Datastore to use when fetching this reference
     * @param mapToProxy        the map to proxy
     * @param referenceObjClass the type of the referenced objects
     * @param ignoreMissing     ignore references that don't exist in the database
     * @return the proxy
     */
    <T extends Map> T createMapProxy(final Datastore datastore, final T mapToProxy, final Class referenceObjClass,
                                     final boolean ignoreMissing);

    /**
     * Creates a proxy for a Class.
     *
     * @param <T>               the type of the entity
     * @param datastore         the Datastore to use when fetching this reference
     * @param targetClass       the referenced object's Class
     * @param key               the Key of the reference
     * @param ignoreMissing     ignore references that don't exist in the database
     * @return the proxy
     */
    <T> T createProxy(final Datastore datastore, Class<T> targetClass, final Key<T> key, final boolean ignoreMissing);

}
