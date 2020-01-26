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

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;

import dev.morphia.Datastore;
import dev.morphia.Key;
import dev.morphia.mapping.Mapper;

/**
 * Defines an Iterator across the Key values for a given type.
 *
 * @param <T> the entity type
 * @author Scott Hernandez this is being replaced by {@link MongoCursor}
 */
public class MorphiaKeyIterator<T> extends MorphiaIterator<T, Key<T>> {
    /**
     * Create
     *
     * @param datastore the Datastore to use when fetching this reference
     * @param cursor the cursor to use
     * @param mapper the Mapper to use
     * @param clazz the original type being iterated
     * @param collection the mongodb collection
     */
    public MorphiaKeyIterator(
            final Datastore datastore,
            final DBCursor cursor,
            final Mapper mapper,
            final Class<T> clazz,
            final String collection) {
        super(datastore, cursor, mapper, clazz, collection, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Key<T> convertItem(final DBObject dbObj) {
        Object id = dbObj.get("_id");
        if (id instanceof DBObject) {
            Class type = getMapper().getMappedClass(getClazz()).getMappedIdField().getType();
            id =
                    getMapper()
                            .fromDBObject(
                                    getDatastore(),
                                    type,
                                    (DBObject) id,
                                    getMapper().createEntityCache());
        }
        return new Key<T>(getClazz(), getCollection(), id);
    }
}
