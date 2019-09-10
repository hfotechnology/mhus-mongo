package de.mhus.lib.mongo.test;

import de.mhus.lib.adb.DbMetadata;
import de.mhus.lib.annotations.adb.DbPersistent;
import de.mhus.lib.errors.MException;

public class MoMetadata extends DbMetadata {

    @DbPersistent
    private String name;

    public MoMetadata() {}
    
    public MoMetadata(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DbMetadata findParentObject() throws MException {
        return null;
    }

}
