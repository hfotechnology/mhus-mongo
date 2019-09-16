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
package dev.morphia.query.validation;

import static java.lang.String.format;

import java.util.List;

import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;

/**
 * Checks the class of the value against the type of the ID for the type.
 */
public final class EntityTypeAndIdValueValidator implements Validator {
    private static final EntityTypeAndIdValueValidator INSTANCE = new EntityTypeAndIdValueValidator();

    private EntityTypeAndIdValueValidator() {
    }
    //TODO: I think this should be possible with the MappedField, not the type

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static EntityTypeAndIdValueValidator getInstance() {
        return INSTANCE;
    }

    /**
     * Checks the class of the {@code value} against the type of the ID for the {@code type}.  Always applies this validation, but there's
     * room to change this to not apply it if, for example, the type is not an entity.
     *
     * @param mappedClass        the MappedClass
     * @param mappedField        the MappedField
     * @param value              the value for the query
     * @param validationFailures the list to add any failures to. If validation passes or {@code appliesTo} returned false, this list will
     *                           not change.    @return true if the validation was applied.
     */
    public boolean apply(final MappedClass mappedClass, final MappedField mappedField, final Object value,
                         final List<ValidationFailure> validationFailures) {
        if (appliesTo(mappedClass, mappedField)) {
            Class classOfValue = value.getClass();
            Class classOfIdFieldForType = mappedClass.getMappedIdField().getConcreteType();
            if (!mappedField.getType().equals(classOfValue) && !classOfValue.equals(classOfIdFieldForType)) {
                validationFailures.add(new ValidationFailure(format("The value class needs to match the type of ID for the field. "
                                                                    + "Value was %s and was a %s and the ID of the type was %s",
                                                                    value, classOfValue, classOfIdFieldForType)));
            }
            return true;
        }
        return false;
    }

    private boolean appliesTo(final MappedClass mappedClass, final MappedField mappedField) {
        return mappedField != null && mappedField.equals(mappedClass.getMappedIdField());
    }
}
