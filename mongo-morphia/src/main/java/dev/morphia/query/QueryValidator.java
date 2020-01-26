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
package dev.morphia.query;

import java.util.List;

import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;
import dev.morphia.query.validation.AllOperationValidator;
import dev.morphia.query.validation.DefaultTypeValidator;
import dev.morphia.query.validation.DoubleTypeValidator;
import dev.morphia.query.validation.EntityAnnotatedValueValidator;
import dev.morphia.query.validation.EntityTypeAndIdValueValidator;
import dev.morphia.query.validation.ExistsOperationValidator;
import dev.morphia.query.validation.GeoWithinOperationValidator;
import dev.morphia.query.validation.InOperationValidator;
import dev.morphia.query.validation.IntegerTypeValidator;
import dev.morphia.query.validation.KeyValueTypeValidator;
import dev.morphia.query.validation.ListValueValidator;
import dev.morphia.query.validation.LongTypeValidator;
import dev.morphia.query.validation.ModOperationValidator;
import dev.morphia.query.validation.NotInOperationValidator;
import dev.morphia.query.validation.PatternValueValidator;
import dev.morphia.query.validation.SizeOperationValidator;
import dev.morphia.query.validation.ValidationFailure;

final class QueryValidator {
    private QueryValidator() {}

    /*package*/
    static boolean isCompatibleForOperator(
            final MappedClass mappedClass,
            final MappedField mappedField,
            final Class<?> type,
            final FilterOperator op,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        // TODO: it's really OK to have null values?  I think this is to prevent null pointers
        // further down,
        // but I want to move the null check into the operations that care whether they allow nulls
        // or not.
        if (value == null || type == null) {
            return true;
        }

        boolean validationApplied =
                ExistsOperationValidator.getInstance()
                                .apply(mappedField, op, value, validationFailures)
                        || SizeOperationValidator.getInstance()
                                .apply(mappedField, op, value, validationFailures)
                        || InOperationValidator.getInstance()
                                .apply(mappedField, op, value, validationFailures)
                        || NotInOperationValidator.getInstance()
                                .apply(mappedField, op, value, validationFailures)
                        || ModOperationValidator.getInstance()
                                .apply(mappedField, op, value, validationFailures)
                        || GeoWithinOperationValidator.getInstance()
                                .apply(mappedField, op, value, validationFailures)
                        || AllOperationValidator.getInstance()
                                .apply(mappedField, op, value, validationFailures)
                        || KeyValueTypeValidator.getInstance()
                                .apply(type, value, validationFailures)
                        || IntegerTypeValidator.getInstance().apply(type, value, validationFailures)
                        || LongTypeValidator.getInstance().apply(type, value, validationFailures)
                        || DoubleTypeValidator.getInstance().apply(type, value, validationFailures)
                        || PatternValueValidator.getInstance()
                                .apply(type, value, validationFailures)
                        || EntityAnnotatedValueValidator.getInstance()
                                .apply(type, value, validationFailures)
                        || ListValueValidator.getInstance().apply(type, value, validationFailures)
                        || EntityTypeAndIdValueValidator.getInstance()
                                .apply(mappedClass, mappedField, value, validationFailures)
                        || DefaultTypeValidator.getInstance()
                                .apply(type, value, validationFailures);

        return validationApplied && validationFailures.isEmpty();
    }
}
