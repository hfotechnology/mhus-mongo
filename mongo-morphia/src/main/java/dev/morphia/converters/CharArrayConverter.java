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
package dev.morphia.converters;

import java.lang.reflect.Array;

import dev.morphia.mapping.MappedField;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
public class CharArrayConverter extends TypeConverter implements SimpleValueConverter {
    /** Creates the Converter. */
    public CharArrayConverter() {
        super(char[].class, Character[].class);
    }

    @Override
    public Object decode(
            final Class targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        final char[] chars = val.toString().toCharArray();
        if (targetClass.isArray() && targetClass.equals(Character[].class)) {
            return convertToWrapperArray(chars);
        }
        return chars;
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof char[]) {
                return new String((char[]) value);
            } else {
                final StringBuilder builder = new StringBuilder();
                final Character[] array = (Character[]) value;
                for (final Character character : array) {
                    builder.append(character);
                }
                return builder.toString();
            }
        }
    }

    Object convertToWrapperArray(final char[] values) {
        final int length = values.length;
        final Object array = Array.newInstance(Character.class, length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, values[i]);
        }
        return array;
    }
}
