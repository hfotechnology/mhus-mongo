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
package dev.morphia.mapping.validation;

import java.util.Set;

import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.Mapper;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public interface ClassConstraint {
    /**
     * Check that a MappedClass meets the constraint
     *
     * @param mc the MappedClass to check
     * @param ve the set of violations
     * @param mapper the Mapper to use for validation
     */
    void check(final Mapper mapper, MappedClass mc, Set<ConstraintViolation> ve);
}
