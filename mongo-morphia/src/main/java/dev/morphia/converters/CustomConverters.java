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

import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.Mapper;

/**
 * Defines a bundle of converters that will delegate to the DefaultConverters for unknown types but
 * provides a chance to override the converter used for different types.
 *
 * @see DefaultConverters
 */
public class CustomConverters extends Converters {
    private final DefaultConverters defaultConverters;

    /**
     * Creates a bundle with a particular Mapper.
     *
     * @param mapper the Mapper to use
     */
    public CustomConverters(final Mapper mapper) {
        super(mapper);
        defaultConverters = new DefaultConverters(mapper);
    }

    @Override
    public boolean isRegistered(final Class<? extends TypeConverter> tcClass) {
        return super.isRegistered(tcClass) || defaultConverters.isRegistered(tcClass);
    }

    @Override
    public void removeConverter(final TypeConverter tc) {
        super.removeConverter(tc);
        defaultConverters.removeConverter(tc);
    }

    @Override
    protected TypeConverter getEncoder(final Class c) {
        TypeConverter encoder = super.getEncoder(c);
        if (encoder == null) {
            encoder = defaultConverters.getEncoder(c);
        }

        if (encoder != null) {
            return encoder;
        }
        throw new ConverterNotFoundException(format("Cannot find encoder for %s", c.getName()));
    }

    @Override
    protected TypeConverter getEncoder(final Object val, final MappedField mf) {
        TypeConverter encoder = super.getEncoder(val, mf);
        if (encoder == null) {
            encoder = defaultConverters.getEncoder(val, mf);
        }

        if (encoder != null) {
            return encoder;
        }

        throw new ConverterNotFoundException(
                format(
                        "Cannot find encoder for %s as need for %s",
                        mf.getType(), mf.getFullName()));
    }
}
