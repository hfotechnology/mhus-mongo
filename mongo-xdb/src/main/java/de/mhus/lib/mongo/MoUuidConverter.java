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
package de.mhus.lib.mongo;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

public class MoUuidConverter extends TypeConverter implements SimpleValueConverter {

    /** Creates the Converter. */
    public MoUuidConverter() {
        super(UUID.class);
    }

    @Override
    public Object decode(
            final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof UUID) {
            return val;
        }

        if (val instanceof ObjectId) {
            return MoUtil.toUUID((ObjectId) val);
        }

        return UUID.fromString(val.toString());
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) return null;

        if (value instanceof ObjectId) return value;

        if (value instanceof UUID) return (MoUtil.toObjectId((UUID) value));

        return value;
    }
}
