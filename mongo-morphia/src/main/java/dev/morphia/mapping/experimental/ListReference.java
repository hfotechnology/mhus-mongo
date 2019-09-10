package dev.morphia.mapping.experimental;

import java.util.Collection;
import java.util.List;

import dev.morphia.Datastore;
import dev.morphia.mapping.MappedClass;

/**
 * @param <T>
 * @morphia.internal
 */
class ListReference<T> extends CollectionReference<List<T>> {
    private List<T> values;

    /**
     * @morphia.internal
     */
    ListReference(final Datastore datastore, final MappedClass mappedClass, final List ids) {
        super(datastore, mappedClass, ids);
    }

    ListReference(final List<T> values) {
        this.values = values;
    }

    @Override
    Collection<?> getValues() {
        return values;
    }

    @Override
    public List<T> get() {
        if (values == null && getIds() != null) {
            values = (List<T>) find();
        }
        return values;
    }

}
