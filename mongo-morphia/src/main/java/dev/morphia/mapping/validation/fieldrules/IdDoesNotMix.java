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
package dev.morphia.mapping.validation.fieldrules;

import java.util.Set;

import de.mhus.lib.annotations.adb.DbPrimaryKey;
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;
import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;

/** @author ScottHenandez */
public class IdDoesNotMix extends FieldConstraint {

    @Override
    protected void check(
            final Mapper mapper,
            final MappedClass mc,
            final MappedField mf,
            final Set<ConstraintViolation> ve) {
        // an @Id field can not be a Value, Reference, or Embedded
        if (mf.hasAnnotation(DbPrimaryKey.class)) {
            if (mf.hasAnnotation(Reference.class)
                    || mf.hasAnnotation(Embedded.class)
                    || mf.hasAnnotation(Property.class)) {
                ve.add(
                        new ConstraintViolation(
                                Level.FATAL,
                                mc,
                                mf,
                                getClass(),
                                mf.getFullName()
                                        + " is annotated as @"
                                        + DbPrimaryKey.class.getSimpleName()
                                        + " and cannot be mixed with other annotations (like @Reference)"));
            }
        }
    }
}
