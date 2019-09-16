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

import java.util.List;
import java.util.Map;

/**
 * Contains static validators for checking your type against various criteria.
 */
final class CollectionTypeValidator implements Validator {
    private CollectionTypeValidator() {
    }

    static boolean typeIsIterableOrArrayOrMap(final Class<?> type) {
        return typeIsAListOrArray(type) || typeIsIterable(type) || typeIsMap(type);
    }

    static boolean typeIsAListOrArray(final Class<?> type) {
        return (List.class.isAssignableFrom(type) || type.isArray());
    }

    static boolean typeIsIterable(final Class<?> type) {
        return Iterable.class.isAssignableFrom(type);
    }

    static boolean typeIsMap(final Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }
}
