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

import static java.lang.String.format;

import java.util.List;

/**
 * If the Type is some sort of integer-compatible field (see {@code getTypeClasses}) then this
 * validator will check if the value is of the correct type for this field.
 */
public final class IntegerTypeValidator extends TypeValidator {
    private static final IntegerTypeValidator INSTANCE = new IntegerTypeValidator();

    private IntegerTypeValidator() {}

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static IntegerTypeValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected boolean appliesTo(final Class<?> type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    protected void validate(
            final Class<?> type,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        if (Integer.class != value.getClass()) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "When type is one of the integer types the value should be an Integer.  "
                                            + "Type was %s and value '%s' was a %s",
                                    type, value, value.getClass())));
        }
    }
}
