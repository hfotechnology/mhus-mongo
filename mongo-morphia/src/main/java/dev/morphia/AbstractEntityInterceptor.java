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
package dev.morphia;


import com.mongodb.DBObject;

import dev.morphia.mapping.Mapper;


/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 */
public class AbstractEntityInterceptor implements EntityInterceptor {

    @Override
    public void postLoad(final Object ent, final DBObject dbObj, final Mapper mapper) {
    }

    @Override
    public void postPersist(final Object ent, final DBObject dbObj, final Mapper mapper) {
    }

    @Override
    public void preLoad(final Object ent, final DBObject dbObj, final Mapper mapper) {
    }

    @Override
    public void prePersist(final Object ent, final DBObject dbObj, final Mapper mapper) {
    }

    @Override
    public void preSave(final Object ent, final DBObject dbObj, final Mapper mapper) {
    }
}
