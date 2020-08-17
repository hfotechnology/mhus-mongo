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
package dev.morphia.mapping;

import java.util.Map;

import com.mongodb.DBObject;

import dev.morphia.Datastore;
import dev.morphia.mapping.cache.EntityCache;

/** @morphia.internal */
public interface CustomMapper {
    /**
     * Creates an entity and populates its state based on the dbObject given. This method is
     * primarily an internal method. Reliance on this method may break your application in future
     * releases.
     *
     * @param datastore the Datastore to use
     * @param dbObject the object state to use
     * @param mf the MappedField with the metadata to use during conversion
     * @param entity the entity to populate
     * @param cache the EntityCache to use to prevent multiple loads of the same entities over and
     *     over
     * @param mapper the Mapper to use
     */
    void fromDBObject(
            final Datastore datastore,
            DBObject dbObject,
            MappedField mf,
            Object entity,
            EntityCache cache,
            Mapper mapper);

    /**
     * Converts an entity to a DBObject. This method is primarily an internal method. Reliance on
     * this method may break your application in future releases.
     *
     * @param entity the entity to convert
     * @param mf the MappedField with the metadata to use during conversion
     * @param dbObject the DBObject to populate
     * @param involvedObjects a Map of objects already seen
     * @param mapper the Mapper to use
     */
    void toDBObject(
            Object entity,
            MappedField mf,
            DBObject dbObject,
            Map<Object, DBObject> involvedObjects,
            Mapper mapper);
}
