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
package dev.morphia;

import com.mongodb.DBObject;

import dev.morphia.annotations.PostLoad;
import dev.morphia.annotations.PostPersist;
import dev.morphia.annotations.PreLoad;
import dev.morphia.annotations.PreSave;
import dev.morphia.mapping.Mapper;

/** Interface for intercepting @Entity lifecycle events */
public interface EntityInterceptor {
    /**
     * @param ent the entity being processed
     * @param dbObj the DBObject form of the entity
     * @param mapper the Mapper being used
     * @see PostLoad
     */
    void postLoad(Object ent, DBObject dbObj, Mapper mapper);

    /**
     * @param ent the entity being processed
     * @param dbObj the DBObject form of the entity
     * @param mapper the Mapper being used
     * @see PostPersist
     */
    void postPersist(Object ent, DBObject dbObj, Mapper mapper);

    /**
     * @param ent the entity being processed
     * @param dbObj the DBObject form of the entity
     * @param mapper the Mapper being used
     * @see PreLoad
     */
    void preLoad(Object ent, DBObject dbObj, Mapper mapper);

    /**
     * @param ent the entity being processed
     * @param dbObj the DBObject form of the entity
     * @param mapper the Mapper being used
     * @see PostPersist
     */
    void prePersist(Object ent, DBObject dbObj, Mapper mapper);

    /**
     * @param ent the entity being processed
     * @param dbObj the DBObject form of the entity
     * @param mapper the Mapper being used
     * @see PreSave
     */
    void preSave(Object ent, DBObject dbObj, Mapper mapper);
}
