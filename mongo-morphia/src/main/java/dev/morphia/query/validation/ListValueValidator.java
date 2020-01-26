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
package dev.morphia.query.validation;

import java.util.List;

/** Validates Lists. Currently a noop. */
// TODO: Trisha - really not sure why lists are always valid values. This should be a real Type
// validator which checks against the field,
// if in fact that's not already done by another validator
public final class ListValueValidator extends ValueValidator {
    private static final ListValueValidator INSTANCE = new ListValueValidator();

    private ListValueValidator() {}

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static ListValueValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class getRequiredValueType() {
        return List.class;
    }

    @Override
    protected void validate(
            final Class<?> type,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        // preserving current behaviour - this never fails validation
    }
}
