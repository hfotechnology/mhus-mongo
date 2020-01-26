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
import java.util.List;

import dev.morphia.mapping.MappedField;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
public class ShortConverter extends TypeConverter implements SimpleValueConverter {
    /** Creates the Converter. */
    public ShortConverter() {
        super(short.class, Short.class, short[].class, Short[].class);
    }

    @Override
    public Object decode(
            final Class targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof Short) {
            return val;
        }

        if (val instanceof Number) {
            return ((Number) val).shortValue();
        }

        if (val instanceof List) {
            final Class<?> type =
                    targetClass.isArray() ? targetClass.getComponentType() : targetClass;
            return convertToArray(type, (List<?>) val);
        }

        return Short.parseShort(val.toString());
    }

    Object convertToArray(final Class type, final List<?> values) {
        final Object array = Array.newInstance(type, values.size());
        for (int i = 0; i < values.size(); i++) {
            Array.set(array, i, ((Number) values.get(i)).shortValue());
        }
        return array;
    }
}
