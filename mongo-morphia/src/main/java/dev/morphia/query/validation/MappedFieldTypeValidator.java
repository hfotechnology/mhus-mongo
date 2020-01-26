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

import dev.morphia.mapping.MappedField;

final class MappedFieldTypeValidator implements Validator {
    private MappedFieldTypeValidator() {}

    static boolean isArrayOfNumbers(final MappedField mappedField) {
        Class subClass = mappedField.getSubClass();
        return mappedField.getType().isArray()
                && (subClass == int.class
                        || subClass == long.class
                        || subClass == double.class
                        || subClass == float.class);
    }

    static boolean isIterableOfNumbers(final MappedField mappedField) {
        return Iterable.class.isAssignableFrom(mappedField.getType())
                && Number.class.isAssignableFrom(mappedField.getSubClass());
    }
}
