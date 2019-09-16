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
package dev.morphia.converters;


import dev.morphia.mapping.MappedField;


/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
public class EnumConverter extends TypeConverter implements SimpleValueConverter {

    @Override
    @SuppressWarnings("unchecked")
    public Object decode(final Class targetClass, final Object fromDBObject, final MappedField optionalExtraInfo) {
        if (fromDBObject == null) {
            return null;
        }
        return Enum.valueOf(targetClass, fromDBObject.toString());
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }

        return getName((Enum) value);
    }

    @Override
    protected boolean isSupported(final Class c, final MappedField optionalExtraInfo) {
        return c.isEnum();
    }

    private <T extends Enum> String getName(final T value) {
        return value.name();
    }
}
