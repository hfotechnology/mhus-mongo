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

/** @author Scott Hernandez */
public enum UpdateOperator {
    SET("$set"),
    SET_ON_INSERT("$setOnInsert"),
    UNSET("$unset"),
    PULL("$pull"),
    PULL_ALL("$pullAll"),
    PUSH("$push"),
    PUSH_ALL("$pushAll"),
    ADD_TO_SET("$addToSet"),
    ADD_TO_SET_EACH("$addToSet"),
    // fake to indicate that the value should be wrapped in an $each
    EACH("$each"),
    POP("$pop"),
    INC("$inc"),
    Foo("$foo"),
    MAX("$max"),
    MIN("$min");

    private final String value;

    UpdateOperator(final String val) {
        value = val;
    }

    /**
     * Creates an UpdateOperator from a String
     *
     * @param val the value to convert
     * @return the UpdateOperator
     */
    public static UpdateOperator fromString(final String val) {
        for (int i = 0; i < values().length; i++) {
            final UpdateOperator fo = values()[i];
            if (fo.sameAs(val)) {
                return fo;
            }
        }
        return null;
    }

    /** @return the value of the UpdateOperator */
    public String val() {
        return value;
    }

    private boolean sameAs(final String val) {
        return value.equals(val);
    }
}
