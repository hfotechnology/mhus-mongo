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
package dev.morphia.query;

import org.bson.types.CodeWScope;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/** Creates a Criteria for a $where clause. */
public class WhereCriteria extends AbstractCriteria {

    private final Object js;

    /**
     * Creates a WhereCriteria with the given javascript
     *
     * @param js the javascript
     */
    public WhereCriteria(final String js) {
        this.js = js;
    }

    /**
     * Creates a WhereCriteria with the given javascript
     *
     * @param js the javascript
     */
    public WhereCriteria(final CodeWScope js) {
        this.js = js;
    }

    @Override
    public DBObject toDBObject() {
        return new BasicDBObject(FilterOperator.WHERE.val(), js);
    }

    @Override
    public String getFieldName() {
        return FilterOperator.WHERE.val();
    }
}
