package de.mhus.lib.mongo;

import java.util.UUID;

import org.bson.types.ObjectId;

import dev.morphia.converters.SimpleValueConverter;
import dev.morphia.converters.TypeConverter;
import dev.morphia.mapping.MappedField;

public class MoUuidConverter extends TypeConverter implements SimpleValueConverter {

    /**
     * Creates the Converter.
     */
    public MoUuidConverter() {
        super(UUID.class);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof UUID) {
            return val;
        }
        
        if (val instanceof ObjectId) {
            return MoUtil.toUUID( (ObjectId)val );
        }

        return  UUID.fromString(val.toString());
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) return null;
        
        if (value instanceof ObjectId) return value;
        
        if (value instanceof UUID) return (MoUtil.toObjectId((UUID) value));
        
        return value;
    }


}
