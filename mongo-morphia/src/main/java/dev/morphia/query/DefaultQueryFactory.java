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
package dev.morphia.query;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import dev.morphia.Datastore;

/** A default implementation of {@link QueryFactory}. */
public class DefaultQueryFactory extends AbstractQueryFactory {

    @Override
    public <T> Query<T> createQuery(
            final Datastore datastore,
            final DBCollection collection,
            final Class<T> type,
            final DBObject query) {

        final QueryImpl<T> item = new QueryImpl<T>(type, collection, datastore);

        if (query != null) {
            item.setQueryObject(query);
        }

        return item;
    }
}
