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

import static java.lang.String.format;

import java.io.IOException;

import org.bson.types.Binary;

import dev.morphia.annotations.Serialized;
import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.MappingException;
import dev.morphia.mapping.Serializer;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class SerializedObjectConverter extends TypeConverter {
    @Override
    public Object decode(final Class targetClass, final Object fromDBObject, final MappedField f) {
        if (fromDBObject == null) {
            return null;
        }

        if (!((fromDBObject instanceof Binary) || (fromDBObject instanceof byte[]))) {
            throw new MappingException(
                    format(
                            "The stored data is not a DBBinary or byte[] instance for %s ; it is a %s",
                            f.getFullName(), fromDBObject.getClass().getName()));
        }

        try {
            final boolean useCompression = !f.getAnnotation(Serialized.class).disableCompression();
            return Serializer.deserialize(fromDBObject, useCompression);
        } catch (IOException e) {
            throw new MappingException("While deserializing to " + f.getFullName(), e);
        } catch (ClassNotFoundException e) {
            throw new MappingException("While deserializing to " + f.getFullName(), e);
        }
    }

    @Override
    public Object encode(final Object value, final MappedField f) {
        if (value == null) {
            return null;
        }
        try {
            final boolean useCompression = !f.getAnnotation(Serialized.class).disableCompression();
            return Serializer.serialize(value, useCompression);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected boolean isSupported(final Class c, final MappedField optionalExtraInfo) {
        return optionalExtraInfo != null && (optionalExtraInfo.hasAnnotation(Serialized.class));
    }
}
