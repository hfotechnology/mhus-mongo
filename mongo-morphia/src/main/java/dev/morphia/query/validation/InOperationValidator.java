package dev.morphia.query.validation;

import static dev.morphia.query.FilterOperator.IN;
import static dev.morphia.query.validation.CollectionTypeValidator.typeIsIterableOrArrayOrMap;
import static java.lang.String.format;

import java.util.List;

import dev.morphia.mapping.MappedField;
import dev.morphia.query.FilterOperator;

/**
 * Checks if the value can have the {@code FilterOperator.IN} operator applied to it.
 */
public final class InOperationValidator extends OperationValidator {
    private static final InOperationValidator INSTANCE = new InOperationValidator();

    private InOperationValidator() {
    }

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static InOperationValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected FilterOperator getOperator() {
        return IN;
    }

    @Override
    protected void validate(final MappedField mappedField, final Object value, final List<ValidationFailure> validationFailures) {
        if (value == null) {
            validationFailures.add(new ValidationFailure(format("For an $in operation, value cannot be null.")));
        } else if (!typeIsIterableOrArrayOrMap(value.getClass())) {
            validationFailures.add(new ValidationFailure(format("For a $in operation, value '%s' should be a List or array or Map. "
                                                                + "Instead it was a: %s",
                                                                value, value.getClass()
                                                               )));
        }
    }
}
