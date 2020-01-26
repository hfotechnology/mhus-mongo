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
package dev.morphia;

import com.mongodb.MongoClient;

import dev.morphia.dao.BasicDAO;

/**
 * Provides a basic DAO for use in applications
 *
 * @param <T> the entity type
 * @param <K> the key type use dev.morphia.dao.BasicDAO
 */
public class DAO<T, K> extends BasicDAO<T, K> {
    /**
     * @param entityClass the type to use with this DAO
     * @param mongoClient the client to use to talk to the database
     * @param morphia the morphia instance to use
     * @param dbName the database to connect to
     */
    public DAO(
            final Class<T> entityClass,
            final MongoClient mongoClient,
            final Morphia morphia,
            final String dbName) {
        super(entityClass, mongoClient, morphia, dbName);
    }

    /**
     * @param entityClass the type to use with this DAO
     * @param ds the datastore to use
     */
    public DAO(final Class<T> entityClass, final Datastore ds) {
        super(entityClass, ds);
    }
}
