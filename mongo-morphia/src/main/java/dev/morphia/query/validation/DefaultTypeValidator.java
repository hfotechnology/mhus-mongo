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

import static java.lang.String.format;

import java.util.List;

/**
 * This is a fall-through validator that looks at the type and at the class of the value and figures out if they're similar enough to be
 * used to query.
 */
public final class DefaultTypeValidator extends TypeValidator {
    private static final DefaultTypeValidator INSTANCE = new DefaultTypeValidator();

    private DefaultTypeValidator() {
    }

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static DefaultTypeValidator getInstance() {
        return INSTANCE;
    }

    /**
     * Always returns true, applies to all types
     *
     * @param type the type to be validated
     * @return true.  Always.
     */
    @Override
    protected boolean appliesTo(final Class<?> type) {
        return true;
    }

    @Override
    protected void validate(final Class<?> type, final Object value, final List<ValidationFailure> validationFailures) {
        if (!type.isAssignableFrom(value.getClass())
            && !value.getClass().getSimpleName().equalsIgnoreCase(type.getSimpleName())) {
            validationFailures.add(new ValidationFailure(format("Type %s may not be queryable with value '%s' with class %s",
                                                                type.getCanonicalName(),
                                                                value, value.getClass().getCanonicalName())));
        }
    }
}
