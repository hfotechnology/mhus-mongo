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
import java.util.UUID;

import dev.morphia.annotations.Serialized;
import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;
import dev.morphia.utils.ReflectionUtils;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class MapKeyDifferentFromString extends FieldConstraint {
    private static final String SUPPORTED = "(Map<String/Enum/Long/ObjectId/..., ?>)";

    @Override
    protected void check(
            final Mapper mapper,
            final MappedClass mc,
            final MappedField mf,
            final Set<ConstraintViolation> ve) {
        if (mf.isMap() && (!mf.hasAnnotation(Serialized.class))) {
            final Class<?> aClass = ReflectionUtils.getParameterizedClass(mf.getField(), 0);
            // WARN if not parameterized : null or Object...
            if (aClass == null || Object.class.equals(aClass)) {
                ve.add(
                        new ConstraintViolation(
                                Level.WARNING,
                                mc,
                                mf,
                                getClass(),
                                "Maps cannot be keyed by Object (Map<Object,?>); Use a parametrized type that is supported "
                                        + SUPPORTED));
            } else if (!aClass.equals(String.class)
                    && !aClass.equals(UUID.class)
                    && !ReflectionUtils.isPrimitiveLike(aClass)) {
                ve.add(
                        new ConstraintViolation(
                                Level.FATAL,
                                mc,
                                mf,
                                getClass(),
                                "Maps must be keyed by a simple type "
                                        + SUPPORTED
                                        + "; "
                                        + aClass
                                        + " is not supported as a map key type."));
            }
        }
    }
}
