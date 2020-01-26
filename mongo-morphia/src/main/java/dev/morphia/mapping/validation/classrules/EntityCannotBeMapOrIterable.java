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
package dev.morphia.mapping.validation.classrules;

import java.util.Map;
import java.util.Set;

import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ClassConstraint;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class EntityCannotBeMapOrIterable implements ClassConstraint {

    @Override
    public void check(
            final Mapper mapper, final MappedClass mc, final Set<ConstraintViolation> ve) {

        if (mc.getEntityAnnotation() != null
                && (Map.class.isAssignableFrom(mc.getClazz())
                        || Iterable.class.isAssignableFrom(mc.getClazz()))) {
            ve.add(
                    new ConstraintViolation(
                            Level.FATAL, mc, getClass(), "Entities cannot implement Map/Iterable"));
        }
    }
}
