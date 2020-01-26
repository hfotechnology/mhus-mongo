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

import static dev.morphia.query.FilterOperator.SIZE;
import static dev.morphia.query.validation.ValueClassValidator.valueIsClassOrSubclassOf;
import static java.lang.String.format;

import java.util.List;

import dev.morphia.mapping.MappedField;
import dev.morphia.query.FilterOperator;

/**
 * Checks if the value can have the {@code FilterOperator.ALL} operator applied to it. Since this
 * class does not need state, and the methods can't be static because it implements an interface, it
 * seems to be one of the few places where the Singleton pattern seems appropriate.
 */
public final class SizeOperationValidator extends OperationValidator {
    private static final SizeOperationValidator INSTANCE = new SizeOperationValidator();

    private SizeOperationValidator() {}

    /**
     * Get the instance
     *
     * @return the Singleton instance of this validator
     */
    public static SizeOperationValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected FilterOperator getOperator() {
        return SIZE;
    }

    @Override
    protected void validate(
            final MappedField mappedField,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        if (!valueIsClassOrSubclassOf(value, Number.class)) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "For a $size operation, value '%s' should be an integer type.  "
                                            + "Instead it was a: %s",
                                    value, value.getClass())));
        }
        if (!CollectionTypeValidator.typeIsIterableOrArrayOrMap(mappedField.getType())) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "For a $size operation, field '%s' should be a List or array.  "
                                            + "Instead it was a: %s",
                                    mappedField, mappedField.getType())));
        }
    }
}
