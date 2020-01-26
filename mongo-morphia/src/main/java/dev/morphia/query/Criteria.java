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

import com.mongodb.DBObject;

/**
 * Internal class for building up query documents.
 *
 * @morphia.internal
 */
public interface Criteria {
    /** @return the DBObject form of this type */
    DBObject toDBObject();

    /**
     * Used to add this Criteria to a CriteriaContainer
     *
     * @param container the container to add to
     */
    void attach(CriteriaContainer container);

    /** @return the field name for the criteria */
    String getFieldName();
}
