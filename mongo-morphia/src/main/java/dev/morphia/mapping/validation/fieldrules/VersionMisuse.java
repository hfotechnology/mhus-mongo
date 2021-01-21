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

import static java.lang.String.format;

import java.util.Set;

import dev.morphia.ObjectFactory;
import dev.morphia.annotations.Version;
import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class VersionMisuse extends FieldConstraint {

    private ObjectFactory creator;

    /**
     * Creates a version validator.
     *
     * @param creator the ObjectFactory to use
     */
    public VersionMisuse(final ObjectFactory creator) {
        this.creator = creator;
    }

    @Override
    protected void check(
            final Mapper mapper,
            final MappedClass mc,
            final MappedField mf,
            final Set<ConstraintViolation> ve) {
        if (mf.hasAnnotation(Version.class)) {
            final Class<?> type = mf.getType();
            if (Long.class.equals(type) || long.class.equals(type)) {

                // TODO: Replace this will a read ObjectFactory call -- requires Mapper instance.
                final Object testInstance = creator.createInstance(mc.getClazz());

                // check initial value
                if (Long.class.equals(type)) {
                    if (mf.getFieldValue(testInstance) != null) {
                        ve.add(
                                new ConstraintViolation(
                                        Level.FATAL,
                                        mc,
                                        mf,
                                        getClass(),
                                        format(
                                                "When using @%s on a Long field, it must be initialized to null.",
                                                Version.class.getSimpleName())));
                    }
                } else if (long.class.equals(type)) {
                    if ((Long) mf.getFieldValue(testInstance) != 0L) {
                        ve.add(
                                new ConstraintViolation(
                                        Level.FATAL,
                                        mc,
                                        mf,
                                        getClass(),
                                        format(
                                                "When using @%s on a long field, it must be initialized to 0.",
                                                Version.class.getSimpleName())));
                    }
                }
            } else {
                ve.add(
                        new ConstraintViolation(
                                Level.FATAL,
                                mc,
                                mf,
                                getClass(),
                                format(
                                        "@%s can only be used on a Long/long field.",
                                        Version.class.getSimpleName())));
            }
        }
    }
}
