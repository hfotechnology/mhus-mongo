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
package dev.morphia.mapping.lazy.proxy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.morphia.Datastore;
import dev.morphia.Key;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class MapObjectReference extends AbstractReference implements ProxiedEntityReferenceMap {

    private static final long serialVersionUID = 1L;
    private final HashMap<Object, Key<?>> keyMap;

    /**
     * Creates a MapObjectReference
     *
     * @param datastore the Datastore to use when fetching this reference
     * @param mapToProxy the map to proxy
     * @param referenceObjClass the referenced objects' Class
     * @param ignoreMissing ignore missing referenced documents
     */
    public MapObjectReference(
            final Datastore datastore,
            final Map mapToProxy,
            final Class referenceObjClass,
            final boolean ignoreMissing) {

        super(datastore, referenceObjClass, ignoreMissing);
        object = mapToProxy;
        keyMap = new LinkedHashMap<Object, Key<?>>();
    }

    // CHECKSTYLE:OFF
    @Override
    public Map<Object, Key<?>> __getReferenceMap() {
        return keyMap;
    }
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF
    @Override
    public void __put(final Object key, final Key k) {
        keyMap.put(key, k);
    }

    @Override
    protected void beforeWriteObject() {
        if (__isFetched()) {
            syncKeys();
            ((Map) object).clear();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object fetch() {
        final Map m = (Map) object;
        m.clear();
        // TODO us: change to getting them all at once and yell according to
        // ignoreMissing in order to a) increase performance and b) resolve
        // equals keys to the same instance
        // that should really be done in datastore.
        for (final Map.Entry<?, Key<?>> e : keyMap.entrySet()) {
            final Key<?> entityKey = e.getValue();
            m.put(e.getKey(), fetch(entityKey));
        }
        return m;
    }

    @SuppressWarnings("unchecked")
    private void syncKeys() {
        final Datastore ds = getDatastore();

        keyMap.clear();
        final Map<Object, Object> map = (Map) object;
        for (final Map.Entry<Object, Object> e : map.entrySet()) {
            keyMap.put(e.getKey(), ds.getKey(e.getValue()));
        }
    }
}
