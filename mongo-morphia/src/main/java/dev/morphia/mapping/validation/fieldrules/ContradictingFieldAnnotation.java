/**
 * Copyright (c) 2008-2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.morphia.mapping.validation.fieldrules;


import java.lang.annotation.Annotation;
import java.util.Set;

import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;


/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 */
public class ContradictingFieldAnnotation extends FieldConstraint {

    private final Class<? extends Annotation> a1;
    private final Class<? extends Annotation> a2;

    /**
     * Creates a ContradictingFieldAnnotation validation with the two incompatible annotations.
     *
     * @param a1 the first annotation
     * @param a2 the second annotation
     */
    public ContradictingFieldAnnotation(final Class<? extends Annotation> a1, final Class<? extends Annotation> a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    @Override
    protected final void check(final Mapper mapper, final MappedClass mc, final MappedField mf, final Set<ConstraintViolation> ve) {
        if (mf.hasAnnotation(a1) && mf.hasAnnotation(a2)) {
            ve.add(new ConstraintViolation(Level.FATAL, mc, mf, getClass(),
                                           String.format("A field can be either annotated with @%s OR @%s, but not both.",
                                                         a1.getSimpleName(), a2.getSimpleName())));
        }
    }
}
