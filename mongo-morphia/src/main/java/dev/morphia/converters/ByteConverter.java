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

import java.lang.reflect.Array;

import dev.morphia.mapping.MappedField;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
public class ByteConverter extends TypeConverter implements SimpleValueConverter {
    /** Creates the Converter. */
    public ByteConverter() {
        super(byte.class, Byte.class, byte[].class, Byte[].class);
    }

    @Override
    public Object decode(
            final Class targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val.getClass().equals(targetClass)) {
            return val;
        }

        if (val instanceof Number) {
            return ((Number) val).byteValue();
        }

        if (targetClass.isArray() && val.getClass().equals(byte[].class)) {
            return convertToWrapperArray((byte[]) val);
        }
        return Byte.parseByte(val.toString());
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value instanceof Byte[]) {
            return super.encode(convertToPrimitiveArray((Byte[]) value), optionalExtraInfo);
        }
        return super.encode(value, optionalExtraInfo);
    }

    Object convertToPrimitiveArray(final Byte[] values) {
        final int length = values.length;
        final Object array = Array.newInstance(byte.class, length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, values[i]);
        }
        return array;
    }

    Object convertToWrapperArray(final byte[] values) {
        final int length = values.length;
        final Object array = Array.newInstance(Byte.class, length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, values[i]);
        }
        return array;
    }
}
