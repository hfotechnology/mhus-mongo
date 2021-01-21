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

/**
 * Internal class to represent groups of {@link Criteria} instances via $and and $or query clauses
 */
public interface CriteriaContainer extends Criteria {
    /**
     * Adds Criteria to this container
     *
     * @param criteria the criteria to add
     * @return
     */
    void add(Criteria... criteria);

    /**
     * Ands Criteria with this CriteriaContainer.
     *
     * @param criteria the criteria
     * @return the container
     */
    CriteriaContainer and(Criteria... criteria);

    /**
     * Creates a criteria against a field
     *
     * @param field the field
     * @return the FieldEnd to define the criteria to apply
     */
    FieldEnd<? extends CriteriaContainer> criteria(String field);

    /**
     * Ors Criteria with this CriteriaContainer.
     *
     * @param criteria the criteria
     * @return the container
     */
    CriteriaContainer or(Criteria... criteria);

    /**
     * Removes Criteria to this container
     *
     * @param criteria the criteria to remove
     */
    void remove(Criteria criteria);
}
