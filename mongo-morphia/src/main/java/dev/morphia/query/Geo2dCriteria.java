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
package dev.morphia.query;


import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

/**
 * Geospatial specific FieldCriteria logic
 */
class Geo2dCriteria extends FieldCriteria {

    private final Map<String, Object> opts;

    Geo2dCriteria(final QueryImpl<?> query, final String field, final FilterOperator op, final Object value,
                  final Map<String, Object> opts) {
        super(query, field, op, value);
        this.opts = opts;
    }

    @Override
    public DBObject toDBObject() {
        final DBObject obj = new BasicDBObject();
        final BasicDBObjectBuilder query;
        switch (getOperator()) {
            case NEAR:
                query = BasicDBObjectBuilder.start(FilterOperator.NEAR.val(), getValue());
                break;
            case NEAR_SPHERE:
                query = BasicDBObjectBuilder.start(FilterOperator.NEAR_SPHERE.val(), getValue());
                break;
            case WITHIN_BOX:
                query = BasicDBObjectBuilder.start().push(FilterOperator.GEO_WITHIN.val()).add(getOperator().val(), getValue());
                break;
            case WITHIN_CIRCLE:
                query = BasicDBObjectBuilder.start().push(FilterOperator.GEO_WITHIN.val()).add(getOperator().val(), getValue());
                break;
            case WITHIN_CIRCLE_SPHERE:
                query = BasicDBObjectBuilder.start().push(FilterOperator.GEO_WITHIN.val()).add(getOperator().val(), getValue());
                break;
            default:
                throw new UnsupportedOperationException(getOperator() + " not supported for geo-query");
        }

        //add options...
        if (opts != null) {
            for (final Map.Entry<String, Object> e : opts.entrySet()) {
                query.append(e.getKey(), e.getValue());
            }
        }

        obj.put(getField(), query.get());

        return obj;
    }
}
