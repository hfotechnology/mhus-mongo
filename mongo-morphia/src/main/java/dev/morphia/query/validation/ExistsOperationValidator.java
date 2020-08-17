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
package dev.morphia.query.validation;

import static dev.morphia.query.FilterOperator.EXISTS;
import static java.lang.String.format;

import java.util.List;

import dev.morphia.mapping.MappedField;
import dev.morphia.query.FilterOperator;

/**
 * Checks if the value can have the {@code FilterOperator.EXISTS} operator applied to it. Since this
 * class does not need state, and the methods can't be static because it implements an interface, it
 * seems to be one of the few places where the Singleton pattern seems appropriate.
 */
public final class ExistsOperationValidator extends OperationValidator {
    private static final ExistsOperationValidator INSTANCE = new ExistsOperationValidator();

    private ExistsOperationValidator() {}

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static ExistsOperationValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected FilterOperator getOperator() {
        return EXISTS;
    }

    @Override
    protected void validate(
            final MappedField mappedField,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        if (value.getClass() != Boolean.class) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "For an $exists operation, value '%s' should be a boolean type.  "
                                            + "Instead it was a: %s",
                                    value, value.getClass())));
        }
    }
}
