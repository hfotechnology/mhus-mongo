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
package dev.morphia.mapping.validation;

import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class ConstraintViolation {
    private final MappedClass clazz;
    private final Class<? extends ClassConstraint> validator;
    private final String message;
    private final Level level;
    private MappedField field;

    /**
     * Creates a violation instance to record invalid mapping metadata
     *
     * @param level the severity of the violation
     * @param clazz the errant class
     * @param field the errant field
     * @param validator the constraint failed
     * @param message the message for the failure
     */
    public ConstraintViolation(
            final Level level,
            final MappedClass clazz,
            final MappedField field,
            final Class<? extends ClassConstraint> validator,
            final String message) {
        this(level, clazz, validator, message);
        this.field = field;
    }

    /**
     * Creates a violation instance to record invalid mapping metadata
     *
     * @param level the severity of the violation
     * @param clazz the errant class
     * @param validator the constraint failed
     * @param message the message for the failure
     */
    public ConstraintViolation(
            final Level level,
            final MappedClass clazz,
            final Class<? extends ClassConstraint> validator,
            final String message) {
        this.level = level;
        this.clazz = clazz;
        this.message = message;
        this.validator = validator;
    }

    /** @return the severity of the violation */
    public Level getLevel() {
        return level;
    }

    /** @return the qualified name of the failing mapping */
    public String getPrefix() {
        final String fn = (field != null) ? field.getJavaFieldName() : "";
        return clazz.getClazz().getName() + "." + fn;
    }

    /** @return a human friendly version of the violation */
    public String render() {
        return String.format(
                "%s complained about %s : %s", validator.getSimpleName(), getPrefix(), message);
    }

    /** Levels of constraint violations */
    public enum Level {
        MINOR,
        INFO,
        WARNING,
        SEVERE,
        FATAL
    }
}
