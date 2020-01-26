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

import java.util.UUID;

import dev.morphia.mapping.MappedField;

/**
 * provided by http://code.google.com/p/morphia/issues/detail?id=320
 *
 * @author stummb
 * @author scotthernandez
 */
public class UUIDConverter extends TypeConverter implements SimpleValueConverter {

    /** Creates the Converter. */
    public UUIDConverter() {
        super(UUID.class);
    }

    @Override
    public Object decode(
            final Class targetClass,
            final Object fromDBObject,
            final MappedField optionalExtraInfo) {
        return fromDBObject == null ? null : UUID.fromString((String) fromDBObject);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        return value == null ? null : value.toString();
    }
}
