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
package dev.morphia.query.validation;

import static dev.morphia.query.FilterOperator.GEO_WITHIN;
import static dev.morphia.query.validation.MappedFieldTypeValidator.isArrayOfNumbers;
import static dev.morphia.query.validation.MappedFieldTypeValidator.isIterableOfNumbers;
import static java.lang.String.format;

import java.util.List;

import com.mongodb.DBObject;

import dev.morphia.mapping.MappedField;
import dev.morphia.query.FilterOperator;

/** Supports validation for queries using the {@code FilterOperator.GEO_WITHIN} operator. */
public final class GeoWithinOperationValidator extends OperationValidator {
    private static final GeoWithinOperationValidator INSTANCE = new GeoWithinOperationValidator();

    private GeoWithinOperationValidator() {}

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static GeoWithinOperationValidator getInstance() {
        return INSTANCE;
    }

    // this could be a lot more rigorous
    private static boolean isValueAValidGeoQuery(final Object value) {
        if (value instanceof DBObject) {
            String key = ((DBObject) value).keySet().iterator().next();
            return key.equals("$box")
                    || key.equals("$center")
                    || key.equals("$centerSphere")
                    || key.equals("$polygon");
        }
        return false;
    }

    @Override
    protected FilterOperator getOperator() {
        return GEO_WITHIN;
    }

    @Override
    protected void validate(
            final MappedField mappedField,
            final Object value,
            final List<ValidationFailure> validationFailures) {
        if (!isArrayOfNumbers(mappedField) && !isIterableOfNumbers(mappedField)) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "For a $geoWithin operation, if field '%s' is an array or iterable it "
                                            + "should have numeric values. Instead it had: %s",
                                    mappedField, mappedField.getSubClass())));
        }
        if (!isValueAValidGeoQuery(value)) {
            validationFailures.add(
                    new ValidationFailure(
                            format(
                                    "For a $geoWithin operation, the value should be a valid geo query. "
                                            + "Instead it was: %s",
                                    value)));
        }
    }
}
