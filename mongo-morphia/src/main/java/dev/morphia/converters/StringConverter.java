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
public class StringConverter extends TypeConverter implements SimpleValueConverter {
    /** Creates the Converter. */
    public StringConverter() {
        super(String.class, String[].class);
    }

    @Override
    public Object decode(
            final Class targetClass,
            final Object fromDBObject,
            final MappedField optionalExtraInfo) {
        if (fromDBObject == null) {
            return null;
        }

        if (targetClass.equals(fromDBObject.getClass())) {
            return fromDBObject;
        }

        if (fromDBObject instanceof List) {
            final Class<?> type =
                    targetClass.isArray() ? targetClass.getComponentType() : targetClass;
            return ReflectionUtils.convertToArray(type, (List<?>) fromDBObject);
        }

        if (targetClass.equals(String[].class)) {
            return new String[] {fromDBObject.toString()};
        }

        return fromDBObject.toString();
    }
}
