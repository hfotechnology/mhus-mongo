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
package dev.morphia.mapping.validation.fieldrules;

import java.util.Set;

import dev.morphia.annotations.Property;
import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class MisplacedProperty extends FieldConstraint {

    @Override
    protected void check(
            final Mapper mapper,
            final MappedClass mc,
            final MappedField mf,
            final Set<ConstraintViolation> ve) {
        // a field can be a Value, Reference, or Embedded
        if (mf.hasAnnotation(Property.class)) {
            // make sure that the property type is supported
            if (mf.isSingleValue()
                    && !mf.isTypeMongoCompatible()
                    && !mapper.getConverters().hasSimpleValueConverter(mf)) {
                ve.add(
                        new ConstraintViolation(
                                Level.WARNING,
                                mc,
                                mf,
                                getClass(),
                                mf.getFullName()
                                        + " is annotated as @"
                                        + Property.class.getSimpleName()
                                        + " but is a type that cannot be mapped simply (type is "
                                        + mf.getType().getName()
                                        + ")."));
            }
        }
    }
}
