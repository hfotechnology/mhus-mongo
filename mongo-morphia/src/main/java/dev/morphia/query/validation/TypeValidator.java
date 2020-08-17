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

import java.util.List;

/** Extend this class to provide specific validation for field types for query validation. */
public abstract class TypeValidator implements Validator {
    /**
     * Apply validation for the given field. If the field is not of a type in the list returned by
     * getTypeClasses, the validation is not applied and this method returns false. If the type is
     * in the list, then the validate method is called to see if the value is of a type that can be
     * applied to the given field type. Errors are appended to the validationFailures list.
     *
     * @param type the Class of the field being queried
     * @param value the value being used for a query
     * @param validationFailures the list to add any failures to. If validation passes or {@code
     *     appliesTo} returned false, this list will not change.
     * @return true if validation was applied, false if this validation doesn't apply to this field
     *     type.
     */
    public boolean apply(
            final Class<?> type,
            final Object value,
            final List<ValidationFailure> validationFailures) {

        if (appliesTo(type)) {
            validate(type, value, validationFailures);
            return true;
        }
        return false;
    }

    /**
     * Used by apply to figure out whether to apply the validation or simply return.
     *
     * @param type the type to be validated
     * @return true if this validator applies to this type, false otherwise
     */
    protected abstract boolean appliesTo(final Class<?> type);

    protected abstract void validate(
            final Class<?> type,
            final Object value,
            final List<ValidationFailure> validationFailures);
}
