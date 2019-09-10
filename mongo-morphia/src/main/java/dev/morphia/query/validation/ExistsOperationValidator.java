package dev.morphia.query.validation;

import static dev.morphia.query.FilterOperator.EXISTS;
import static java.lang.String.format;

import java.util.List;

import dev.morphia.mapping.MappedField;
import dev.morphia.query.FilterOperator;

/**
 * Checks if the value can have the {@code FilterOperator.EXISTS} operator applied to it.  Since this class does not need state, and the
 * methods can't be static because it implements an interface, it seems to be one of the few places where the Singleton pattern seems
 * appropriate.
 */
public final class ExistsOperationValidator extends OperationValidator {
    private static final ExistsOperationValidator INSTANCE = new ExistsOperationValidator();

    private ExistsOperationValidator() {
    }

    /**
     * Get the instance.
     *
     * @return the Singleton instance of this validator
     */
    public static ExistsOperationValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected FilterOperator getOperator() {
        return EXISTS;
    }

    @Override
    protected void validate(final MappedField mappedField, final Object value, final List<ValidationFailure> validationFailures) {
        if (value.getClass() != Boolean.class) {
            validationFailures.add(new ValidationFailure(format("For an $exists operation, value '%s' should be a boolean type.  "
                                                                + "Instead it was a: %s", value, value.getClass())));
        }
    }
}
