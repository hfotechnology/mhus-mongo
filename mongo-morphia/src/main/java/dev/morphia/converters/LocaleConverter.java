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

import java.util.Locale;

import dev.morphia.mapping.MappedField;

/**
 * Converts a Locale to/from a valid database structure.
 */
public class LocaleConverter extends TypeConverter implements SimpleValueConverter {

    /**
     * Creates the Converter.
     */
    public LocaleConverter() {
        super(Locale.class);
    }

    @Override
    public Object decode(final Class targetClass, final Object fromDBObject, final MappedField optionalExtraInfo) {
        return parseLocale(fromDBObject.toString());
    }

    @Override
    public Object encode(final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        return val.toString();
    }

    Locale parseLocale(final String localeString) {
        if ((localeString != null) && (localeString.length() != 0)) {
            final int index = localeString.indexOf("_");
            final int index2 = localeString.indexOf("_", index + 1);
            Locale resultLocale;
            if (index == -1) {
                resultLocale = new Locale(localeString);
            } else if (index2 == -1) {
                resultLocale = new Locale(localeString.substring(0, index), localeString.substring(index + 1));
            } else {
                resultLocale = new Locale(
                                             localeString.substring(0, index),
                                             localeString.substring(index + 1, index2),
                                             localeString.substring(index2 + 1));

            }
            return resultLocale;
        }

        return null;
    }
}
