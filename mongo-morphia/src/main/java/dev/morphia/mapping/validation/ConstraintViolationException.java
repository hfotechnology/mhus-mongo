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
package dev.morphia.mapping.validation;

import java.util.Collection;

import dev.morphia.mapping.MappingException;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public class ConstraintViolationException extends MappingException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a ConstraintViolationException with the set of violations
     *
     * @param ve the violations
     */
    public ConstraintViolationException(final Collection<ConstraintViolation> ve) {
        super(createString(ve.toArray(new ConstraintViolation[ve.size()])));
    }

    /**
     * Creates a ConstraintViolationException with the set of violations
     *
     * @param ve the violations
     */
    public ConstraintViolationException(final ConstraintViolation... ve) {
        super(createString(ve));
    }

    private static String createString(final ConstraintViolation[] ve) {
        final StringBuilder sb = new StringBuilder(128);
        sb.append("Number of violations: " + ve.length + " \n");
        for (final ConstraintViolation validationError : ve) {
            sb.append(validationError.render());
        }
        return sb.toString();
    }
}
