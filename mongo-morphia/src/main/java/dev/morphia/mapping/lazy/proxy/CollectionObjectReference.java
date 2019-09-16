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
package dev.morphia.mapping.lazy.proxy;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dev.morphia.Datastore;
import dev.morphia.Key;

/**
 * A list of proxied elements
 *
 * @param <T> the type of the proxied items
 */
public class CollectionObjectReference<T> extends AbstractReference implements ProxiedEntityReferenceList {

    private static final long serialVersionUID = 1L;
    private final List<Key<?>> listOfKeys;

    /**
     * Creates a CollectionObjectReference
     *
     * @param type              the collection
     * @param referenceObjClass the Class of the referenced objects
     * @param ignoreMissing     ignore missing referenced documents
     * @param datastore         the Datastore to use when fetching this reference
     */
    public CollectionObjectReference(final Collection<T> type, final Class<T> referenceObjClass, final boolean ignoreMissing,
                                     final Datastore datastore) {

        super(datastore, referenceObjClass, ignoreMissing);

        object = type;
        listOfKeys = new ArrayList<Key<?>>();
    }

    @Override
    //CHECKSTYLE:OFF
    public void __add(final Key key) {
        //CHECKSTYLE:ON
        listOfKeys.add(key);
    }

    @Override
    //CHECKSTYLE:OFF
    public void __addAll(final Collection<? extends Key<?>> keys) {
        //CHECKSTYLE:ON
        listOfKeys.addAll(keys);
    }

    //CHECKSTYLE:OFF
    @Override
    public List<Key<?>> __getKeysAsList() {
        return Collections.unmodifiableList(listOfKeys);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void beforeWriteObject() {
        if (__isFetched()) {
            syncKeys();
            ((Collection<T>) object).clear();
        }
    }
    //CHECKSTYLE:ON

    @Override
    @SuppressWarnings("unchecked")
    protected synchronized Object fetch() {
        final Collection<T> c = (Collection<T>) object;
        c.clear();

        final int numberOfEntitiesExpected = listOfKeys.size();
        // does not retain order:
        // List<T> retrievedEntities = p.get().getByKeys(referenceObjClass,
        // (List) __getKeysAsList());

        // so we do it the lousy way: FIXME
        final List<T> retrievedEntities = new ArrayList<T>(listOfKeys.size());
        for (final Key<?> k : listOfKeys) {
            T entity = (T) getDatastore().getByKey(referenceObjClass, k);
            if (entity != null) {
                retrievedEntities.add(entity);
            }
        }

        if (!ignoreMissing && (numberOfEntitiesExpected != retrievedEntities.size())) {
            throw new LazyReferenceFetchingException(format("During the lifetime of a proxy of type '%s', some referenced Entities"
                                                                + " of type '%s' have disappeared from the Datastore.",
                                                            c.getClass().getSimpleName(), referenceObjClass.getSimpleName()));
        }

        c.addAll(retrievedEntities);
        return c;
    }

    private void syncKeys() {
        final Datastore ds = getDatastore();

        listOfKeys.clear();
        for (final Object e : ((Collection) object)) {
            listOfKeys.add(ds.getKey(e));
        }
    }
}
