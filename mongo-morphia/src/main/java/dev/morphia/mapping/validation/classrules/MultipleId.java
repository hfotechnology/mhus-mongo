package dev.morphia.mapping.validation.classrules;


import java.util.List;
import java.util.Set;

import de.mhus.lib.annotations.adb.DbPrimaryKey;
import dev.morphia.mapping.MappedClass;
import dev.morphia.mapping.MappedField;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.validation.ClassConstraint;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;


/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 */
public class MultipleId implements ClassConstraint {

    @Override
    public void check(final Mapper mapper, final MappedClass mc, final Set<ConstraintViolation> ve) {

        final List<MappedField> idFields = mc.getFieldsAnnotatedWith(DbPrimaryKey.class);

        if (idFields.size() > 1) {
            ve.add(new ConstraintViolation(Level.FATAL, mc, getClass(),
                                           String.format("More than one @%s Field found (%s).",
                                                         DbPrimaryKey.class.getSimpleName(),
                                                         new FieldEnumString(idFields))));
        }
    }

}
