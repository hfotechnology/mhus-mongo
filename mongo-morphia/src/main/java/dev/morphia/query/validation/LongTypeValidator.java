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
package dev.morphia.query.validation;

import static java.lang.String.format;

import java.util.List;

/** Checks the type of the value if the type of the field is a Long or long. */
public class LongTypeValidator extends TypeValidator {
    private static final LongTypeValidator INSTANCE = new LongTypeValidator();

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static LongTypeValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected boolean appliesTo(final Class<?> type) {
        return type == long.class || type == Long.class;
    }

    @Override
    protected void validate(
            final Class<?> type,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        if (value.getClass() != long.class
                && value.getClass() != Long.class
                && value.getClass() != int.class
                && value.getClass() != Integer.class) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "When type is a long the value should be a long or integer.  "
                                            + "Type was %s and value '%s' was a %s",
                                    type, value, value.getClass())));
        }
    }
}
