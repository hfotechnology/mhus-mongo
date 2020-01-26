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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import de.mhus.lib.annotations.adb.DbPrimaryKey;
import dev.morphia.annotations.Reference;
import dev.morphia.annotations.Transient;
import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ClassConstraint;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;
import dev.morphia.utils.ReflectionUtils;

/** @author josephpachod */
public class ContainsEmbeddedWithId implements ClassConstraint {

    @Override
    public void check(
            final Mapper mapper, final MappedClass mc, final Set<ConstraintViolation> ve) {
        final Set<Class<?>> classesToInspect = new HashSet<Class<?>>();
        for (final Field field :
                ReflectionUtils.getDeclaredAndInheritedFields(mc.getClazz(), true)) {
            if (isFieldToInspect(field) && !field.isAnnotationPresent(DbPrimaryKey.class)) {
                classesToInspect.add(field.getType());
            }
        }
        checkRecursivelyHasNoIdAnnotationPresent(classesToInspect, new HashSet<Class<?>>(), mc, ve);
    }

    private void checkRecursivelyHasNoIdAnnotationPresent(
            final Set<Class<?>> classesToInspect,
            final HashSet<Class<?>> alreadyInspectedClasses,
            final MappedClass mc,
            final Set<ConstraintViolation> ve) {
        for (final Class<?> clazz : classesToInspect) {
            if (alreadyInspectedClasses.contains(clazz)) {
                continue;
            }
            if (hasTypeFieldAnnotation(clazz, DbPrimaryKey.class)) {
                ve.add(
                        new ConstraintViolation(
                                Level.FATAL,
                                mc,
                                getClass(),
                                "You cannot use @Id on any field of an Embedded/Property object"));
            }
            alreadyInspectedClasses.add(clazz);
            final Set<Class<?>> extraClassesToInspect = new HashSet<Class<?>>();
            for (final Field field : ReflectionUtils.getDeclaredAndInheritedFields(clazz, true)) {
                if (isFieldToInspect(field)) {
                    extraClassesToInspect.add(field.getType());
                }
            }
            checkRecursivelyHasNoIdAnnotationPresent(
                    extraClassesToInspect, alreadyInspectedClasses, mc, ve);
        }
    }

    private boolean hasTypeFieldAnnotation(final Class<?> type, final Class<DbPrimaryKey> class1) {
        for (final Field field : ReflectionUtils.getDeclaredAndInheritedFields(type, true)) {
            if (field.getAnnotation(class1) != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isFieldToInspect(final Field field) {
        return (!field.isAnnotationPresent(Transient.class)
                        && !field.isAnnotationPresent(Reference.class))
                && !Modifier.isTransient(field.getModifiers());
    }
}
