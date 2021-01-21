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
package dev.morphia.mapping;

import java.util.Map;

import com.mongodb.DBObject;

import dev.morphia.Datastore;
import dev.morphia.mapping.cache.EntityCache;

/** @morphia.internal */
class ValueMapper implements CustomMapper {
    @Override
    public void fromDBObject(
            final Datastore datastore,
            final DBObject dbObject,
            final MappedField mf,
            final Object entity,
            final EntityCache cache,
            final Mapper mapper) {
        mapper.getConverters().fromDBObject(dbObject, mf, entity);
    }

    @Override
    public void toDBObject(
            final Object entity,
            final MappedField mf,
            final DBObject dbObject,
            final Map<Object, DBObject> involvedObjects,
            final Mapper mapper) {
        try {
            mapper.getConverters().toDBObject(entity, mf, dbObject, mapper.getOptions());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
