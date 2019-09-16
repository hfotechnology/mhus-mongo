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

import static dev.morphia.query.FilterOperator.MOD;
import static java.lang.String.format;

import java.lang.reflect.Array;
import java.util.List;

import dev.morphia.mapping.MappedField;
import dev.morphia.query.FilterOperator;
import dev.morphia.utils.ReflectionUtils;


/**
 * Validates queries using the {@code FilterOperator.MOD}.
 *
 * @mongodb.driver.manual reference/operator/query/mod/ $mod
 */
public final class ModOperationValidator extends OperationValidator {
    private static final ModOperationValidator INSTANCE = new ModOperationValidator();

    private ModOperationValidator() {
    }

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static ModOperationValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected FilterOperator getOperator() {
        return MOD;
    }

    @Override
    protected void validate(final MappedField mappedField, final Object value, final List<ValidationFailure> validationFailures) {
        if (value == null) {
            validationFailures.add(new ValidationFailure("For a $mod operation, value cannot be null."));
        } else if (value.getClass().isArray()) {
            if (Array.getLength(value) != 2) {
                validationFailures.add(new ValidationFailure(format("For a $mod operation, value '%s' should be an array with two integer"
                                                                    + " elements.  Instead it had %s", value, Array.getLength(value))));

            }
            if (!(ReflectionUtils.isIntegerType(value.getClass().getComponentType()))) {
                validationFailures.add(new ValidationFailure(format("Array value needs to contain integers for $mod, but contained: %s",
                                                                    value.getClass().getComponentType())));
            }
        } else {
            validationFailures.add(new ValidationFailure(format("For a $mod operation, value '%s' should be an integer array.  "
                                                                + "Instead it was a: %s",
                                                                value, value.getClass()
                                                               )));
        }
    }

    //    { field: { $mod: [ divisor, remainder ] } }
    //    Changed in version 2.6: The $mod operator errors when passed an array with fewer or more elements. In previous versions,
    // if passed an array with one element, the $mod operator uses 0 as the remainder value, and if passed an array with more than two
    // elements, the $mod ignores all but the first two elements. Previous versions do return an error when passed an empty array. See
    // Not Enough Elements Error and Too Many Elements Error for details.
}
