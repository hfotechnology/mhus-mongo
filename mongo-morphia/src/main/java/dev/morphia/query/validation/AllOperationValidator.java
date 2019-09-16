/**
 * Copyright (c) 2008-2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.morphia.query.validation;

import static dev.morphia.query.FilterOperator.ALL;
import static dev.morphia.query.validation.CollectionTypeValidator.typeIsIterableOrArrayOrMap;
import static java.lang.String.format;

import java.util.List;

import dev.morphia.mapping.MappedField;
import dev.morphia.query.FilterOperator;

/**
 * Validates a query that uses the FilterOperator.ALL operator.
 */
public final class AllOperationValidator extends OperationValidator {
    private static final AllOperationValidator INSTANCE = new AllOperationValidator();

    private AllOperationValidator() {
    }

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static AllOperationValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected FilterOperator getOperator() {
        return ALL;
    }

    @Override
    protected void validate(final MappedField mappedField, final Object value, final List<ValidationFailure> validationFailures) {
        if (value == null) {
            validationFailures.add(new ValidationFailure(format("For an $all operation, value cannot be null.")));
        } else if (!typeIsIterableOrArrayOrMap(value.getClass())) {
            validationFailures.add(new ValidationFailure(format("For an $all operation, value '%s' should be an array, "
                                                                + "an Iterable, or a Map.  Instead it was a: %s",
                                                                value, value.getClass()
                                                               )));
        }
    }

}
