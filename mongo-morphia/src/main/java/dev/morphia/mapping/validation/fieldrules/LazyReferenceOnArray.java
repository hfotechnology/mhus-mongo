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
package dev.morphia.mapping.validation.fieldrules;

import java.util.Set;

import dev.morphia.annotations.Reference;
import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class LazyReferenceOnArray extends FieldConstraint {

    @Override
    protected void check(
            final Mapper mapper,
            final MappedClass mc,
            final MappedField mf,
            final Set<ConstraintViolation> ve) {
        final Reference ref = mf.getAnnotation(Reference.class);
        if (ref != null && ref.lazy()) {
            final Class type = mf.getType();
            if (type.isArray()) {
                ve.add(
                        new ConstraintViolation(
                                Level.FATAL,
                                mc,
                                mf,
                                getClass(),
                                "The lazy attribute cannot be used for an Array. If you need a lazy array "
                                        + "please use ArrayList instead."));
            }
        }
    }
}
