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

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Defines $meta expression object
 *
 * @mongodb.driver.manual reference/operator/aggregation/meta/ $meta
 */
public class Meta {

    private static final String META = "$meta";

    /**
     * Representing specified metadata keyword
     *
     * @mongodb.driver.manual reference/operator/aggregation/meta/#exp._S_meta $meta
     */
    public enum MetaDataKeyword {
        textScore;

        /** @return MetaDataKeyword name */
        public String getName() {
            return textScore.name();
        }
    }

    private MetaDataKeyword metaDataKeyword;
    private String field;

    /**
     * Specify the meta
     *
     * @param metaDataKeyword - metadata keyword to create
     */
    public Meta(final MetaDataKeyword metaDataKeyword) {
        this.metaDataKeyword = metaDataKeyword;
        this.field = metaDataKeyword.getName();
    }

    /**
     * @param metaDataKeyword - metadata keyword to create
     * @param field - metadata object field name
     */
    public Meta(final MetaDataKeyword metaDataKeyword, final String field) {
        this.metaDataKeyword = metaDataKeyword;
        this.field = field;
    }

    /** @return metadata object field name */
    public String getField() {
        return field;
    }

    /**
     * factory method for textScore metaDataKeyword
     *
     * @return instance of 'textScore' Meta
     */
    public static Meta textScore() {
        return new Meta(MetaDataKeyword.textScore);
    }

    /**
     * @param field - the field to project meta data
     * @return instance of 'textScore' Meta
     */
    public static Meta textScore(final String field) {
        return new Meta(MetaDataKeyword.textScore, field);
    }

    DBObject toDatabase() {
        BasicDBObject metaObject = new BasicDBObject(META, metaDataKeyword.getName());
        return new BasicDBObject(field, metaObject);
    }
}
