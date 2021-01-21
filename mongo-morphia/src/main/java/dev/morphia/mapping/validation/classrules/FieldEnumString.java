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
package dev.morphia.mapping.validation.classrules;

import java.util.Arrays;
import java.util.List;

import dev.morphia.mapping.MappedField;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class FieldEnumString {
    private final String display;

    /**
     * Creates a FieldEnumString for the given fields
     *
     * @param fields the fields to use
     */
    public FieldEnumString(final MappedField... fields) {
        this(Arrays.asList(fields));
    }

    /**
     * Creates a FieldEnumString for the given fields
     *
     * @param fields the fields to use
     */
    public FieldEnumString(final List<MappedField> fields) {
        final StringBuilder sb = new StringBuilder(128);
        for (final MappedField mappedField : fields) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(mappedField.getNameToStore());
        }
        display = sb.toString();
    }

    @Override
    public String toString() {
        return display;
    }
}
