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

import static java.lang.String.format;

import java.util.List;

/**
 * Validation for fields of Double/double type. Allows numbers that could be compared against double
 * as the values.
 */
public final class DoubleTypeValidator extends TypeValidator {
    private static final DoubleTypeValidator INSTANCE = new DoubleTypeValidator();

    private DoubleTypeValidator() {}

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static DoubleTypeValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected boolean appliesTo(final Class<?> type) {
        return type == double.class || type == Double.class;
    }

    @Override
    protected void validate(
            final Class<?> type,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        if (!(value instanceof Integer || value instanceof Long || value instanceof Double)) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "When type is a double the value should be compatible with double.  "
                                            + "Type was %s and value '%s' was a %s",
                                    type.getCanonicalName(),
                                    value,
                                    value.getClass().getCanonicalName())));
        }
    }
}
