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
import java.util.regex.Pattern;

/** Validates query values that are Pattern to check the field type is a String. */
public final class PatternValueValidator extends ValueValidator {
    private static final PatternValueValidator INSTANCE = new PatternValueValidator();

    private PatternValueValidator() {}

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static PatternValueValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class getRequiredValueType() {
        return Pattern.class;
    }

    @Override
    protected void validate(
            final Class<?> type,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        if (!String.class.equals(type)) {
            validationFailures.add(
                    new ValidationFailure(
                            format("Patterns can only be used as query values for Strings")));
        }
    }
}
