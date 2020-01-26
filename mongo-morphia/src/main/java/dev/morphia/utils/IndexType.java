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
package dev.morphia.utils;

/** Defines the type of the index to create for a field. */
public enum IndexType {
    ASC(1),
    DESC(-1),
    GEO2D("2d"),
    GEO2DSPHERE("2dsphere"),
    HASHED("hashed"),
    TEXT("text");

    private final Object type;

    IndexType(final Object o) {
        type = o;
    }

    /**
     * Returns the enum instance for the given value
     *
     * @param value the value to find
     * @return the enum instance
     * @since 1.3
     */
    public static IndexType fromValue(final Object value) {
        for (IndexType indexType : values()) {
            if (indexType.type.equals(value)) {
                return indexType;
            }
        }
        throw new IllegalArgumentException("No enum value found for " + value);
    }

    /**
     * Returns the value as needed by the index definition document
     *
     * @return the value
     */
    public Object toIndexValue() {
        return type;
    }
}
