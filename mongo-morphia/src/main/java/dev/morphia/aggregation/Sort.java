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
package dev.morphia.aggregation;

/**
 * Defines a sort stage in an aggregation pipeline
 *
 * @mongodb.driver.manual reference/operator/aggregation/sort/ $sort Use {@link
 *     dev.morphia.query.Sort} instead.
 */
public class Sort extends dev.morphia.query.Sort {

    /**
     * Creates a sort on a field with a direction.
     *
     * <ul>
     *   <li>1 to specify ascending order.
     *   <li>-1 to specify descending order.
     * </ul>
     *
     * @param field the field
     * @param direction the direction
     */
    public Sort(final String field, final int direction) {
        super(field, direction);
    }

    /**
     * Creates an ascending sort on a field
     *
     * @param field the field
     * @return the Sort instance Use {@link dev.morphia.query.Sort#ascending(String)} instead.
     */
    public static Sort ascending(final String field) {
        return new Sort(field, 1);
    }

    /**
     * Creates a descending sort on a field
     *
     * @param field the field
     * @return the Sort instance Use {@link dev.morphia.query.Sort#descending(String)} instead.
     */
    public static Sort descending(final String field) {
        return new Sort(field, -1);
    }

    /** @return the sort direction Use {@link dev.morphia.query.Sort#getOrder()} instead. */
    public int getDirection() {
        return super.getOrder();
    }
}
