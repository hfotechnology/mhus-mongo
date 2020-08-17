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
package dev.morphia.converters;

import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.MappingException;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
public class CharacterConverter extends TypeConverter implements SimpleValueConverter {
    /** Creates the Converter. */
    public CharacterConverter() {
        super(char.class, Character.class);
    }

    @Override
    public Object decode(
            final Class targetClass,
            final Object fromDBObject,
            final MappedField optionalExtraInfo) {
        if (fromDBObject == null) {
            return null;
        }

        if (fromDBObject instanceof String) {
            final char[] chars = ((String) fromDBObject).toCharArray();
            if (chars.length == 1) {
                return chars[0];
            } else if (chars.length == 0) {
                return (char) 0;
            }
        }
        throw new MappingException(
                "Trying to map multi-character data to a single character: " + fromDBObject);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        return value == null || value.equals('\0') ? null : String.valueOf(value);
    }
}
