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

/**
 * Defines BSON types for use in querying against field types.
 *
 * @author suresh chaudhari
 */
public enum Type {
    DOUBLE(1),

    STRING(2),

    OBJECT(3),

    ARRAY(4),

    BINARY_DATA(5),

    UNDEFINED(6),

    OBJECT_ID(7),

    BOOLEAN(8),

    DATE(9),

    NULL(10),

    REGULAR_EXPRESSION(11),

    JAVASCRIPT(13),

    SYMBOL(14),

    JAVASCRIPT_WITH_SCOPE(15),

    INTEGER_32_BIT(16),

    TIMESTAMP(17),

    INTEGER_64_BIT(18),

    MIN_KEY(255),

    MAX_KEY(127);

    private final int value;

    Type(final int value) {
        this.value = value;
    }

    /** @return the BSON type value */
    public int val() {
        return value;
    }
}
