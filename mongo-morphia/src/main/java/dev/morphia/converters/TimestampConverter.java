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

import java.sql.Timestamp;
import java.util.Date;

import dev.morphia.mapping.MappedField;

/** @author scotthernandez */
public class TimestampConverter extends DateConverter {

    /** Creates the Converter. */
    public TimestampConverter() {
        super(Timestamp.class);
    }

    @Override
    public Object decode(
            final Class targetClass, final Object val, final MappedField optionalExtraInfo) {
        final Date d = (Date) super.decode(targetClass, val, optionalExtraInfo);
        return new Timestamp(d.getTime());
    }

    @Override
    public Object encode(final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }
        return new Date(((Timestamp) val).getTime());
    }
}
