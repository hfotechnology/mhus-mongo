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

import dev.morphia.Key;

/** This makes sure that the field type and the Key type match. */
public final class KeyValueTypeValidator extends ValueValidator {
    private static final KeyValueTypeValidator INSTANCE = new KeyValueTypeValidator();

    private KeyValueTypeValidator() {}

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static KeyValueTypeValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getRequiredValueType() {
        return Key.class;
    }

    @Override
    protected void validate(
            final Class<?> type,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        if (!type.equals(((Key) value).getType()) && !type.equals(Key.class)) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "When value is a Key, the type needs to be the right kind of class. "
                                            + "Type was %s and value was '%s'",
                                    type, value)));
        }
    }
}
