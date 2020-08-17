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

import java.util.List;

import dev.morphia.mapping.MappedField;
import dev.morphia.utils.ReflectionUtils;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
public class IntegerConverter extends TypeConverter implements SimpleValueConverter {
    /** Creates the Converter. */
    public IntegerConverter() {
        super(int.class, Integer.class, int[].class, Integer[].class);
    }

    @Override
    public Object decode(
            final Class targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof Integer) {
            return val;
        }

        if (val instanceof Number) {
            return ((Number) val).intValue();
        }

        if (val instanceof List) {
            final Class<?> type =
                    targetClass.isArray() ? targetClass.getComponentType() : targetClass;
            return ReflectionUtils.convertToArray(type, (List<?>) val);
        }

        return Integer.parseInt(val.toString());
    }
}
