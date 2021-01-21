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
package dev.morphia.converters;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import dev.morphia.mapping.MappedField;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
public class EnumSetConverter extends TypeConverter implements SimpleValueConverter {

    private final EnumConverter ec = new EnumConverter();

    /** Creates the Converter. */
    public EnumSetConverter() {
        super(EnumSet.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object decode(
            final Class targetClass,
            final Object fromDBObject,
            final MappedField optionalExtraInfo) {
        if (fromDBObject == null) {
            return null;
        }

        final Class enumType = optionalExtraInfo.getSubClass();

        final List l = (List) fromDBObject;
        if (l.isEmpty()) {
            return EnumSet.noneOf(enumType);
        }

        final List enums = new ArrayList();
        for (final Object object : l) {
            enums.add(ec.decode(enumType, object));
        }
        return EnumSet.copyOf(enums);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }

        final List values = new ArrayList();

        final EnumSet s = (EnumSet) value;
        final Object[] array = s.toArray();
        for (final Object anArray : array) {
            values.add(ec.encode(anArray));
        }

        return values;
    }
}
