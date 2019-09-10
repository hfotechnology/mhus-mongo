package de.mhus.lib.mongo.test;

import java.util.List;

import de.mhus.lib.adb.Persistable;
import de.mhus.lib.mongo.MoSchema;

public class Schema extends MoSchema {

    @Override
    public void findObjectTypes(List<Class<? extends Persistable>> list) {
        list.add(Employee.class);
        list.add(MoMetadata.class);
    }

    @Override
    public String getDatabaseName() {
        return "test";
    }

}