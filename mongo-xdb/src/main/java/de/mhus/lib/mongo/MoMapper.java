/**
 * Copyright (C) 2020 Mike Hummel (mh@mhus.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mhus.lib.mongo;

import java.util.HashSet;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.CustomMapper;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.cache.EntityCache;

import com.mongodb.DBObject;

import de.mhus.lib.adb.DbComfortableObject;

public class MoMapper extends Mapper {

    private MoManager moManager;

    public MoMapper(MoManager moManager) {
        this.moManager = moManager;
        getOptions().setValueMapper(createValueMapper());
        getOptions().setReferenceMapper(createReferenceMapper());
        getOptions().setEmbeddedMapper(createEmbeddedMapper());
        getOptions().setDefaultMapper(createCustomMapper());
        getConverters().addConverter(new MoUuidConverter());
        //        addInterceptor(new MoEntityInterceptor());
    }

    //    @Override
    //    protected Class<? extends Annotation> getFieldAnnotation(final MappedField mf) {
    //        Class<? extends Annotation> res = super.getFieldAnnotation(mf);
    //        if (res == null) {
    //            {
    //                DbPrimaryKey ann = mf.getAnnotation(DbPrimaryKey.class);
    //                if (ann != null) return Property.class;
    //            }
    //            {
    //                DbPersistent ann = mf.getAnnotation(DbPersistent.class);
    //                if (ann != null) return Property.class;
    //            }
    //            {
    //                Embedded ann = mf.getAnnotation(Embedded.class);
    //                if (ann != null) return dev.morphia.annotations.Embedded.class;
    //            }
    //        }
    //        return res;
    //    }

    //    @Override
    //    protected void readMappedField(
    //            final Datastore datastore,
    //            final MappedField mf,
    //            final Object entity,
    //            final EntityCache cache,
    //            final DBObject dbObject) {
    //        CustomMapper mapper;
    //        if (mf.hasAnnotation(Property.class)
    //                || mf.hasAnnotation(DbPrimaryKey.class)
    //                || mf.hasAnnotation(DbPersistent.class)
    //                || mf.hasAnnotation(Serialized.class)
    //                || mf.isTypeMongoCompatible()
    //                || getConverters().hasSimpleValueConverter(mf)) {
    //            mapper = opts.getValueMapper();
    //        } else if (mf.hasAnnotation(Embedded.class)
    //                || mf.hasAnnotation(dev.morphia.annotations.Embedded.class)) {
    //            mapper = opts.getEmbeddedMapper();
    //        } else if (mf.hasAnnotation(Reference.class)
    //                || MorphiaReference.class == mf.getConcreteType()) {
    //            mapper = opts.getReferenceMapper();
    //        } else {
    //            mapper = opts.getDefaultMapper();
    //        }
    //        mapper.fromDBObject(datastore, dbObject, mf, entity, cache, this);
    //    }

    //    @Override
    //    protected void writeMappedField(
    //            final DBObject dbObject,
    //            final MappedField mf,
    //            final Object entity,
    //            final Map<Object, DBObject> involvedObjects) {
    //
    //        // skip not saved fields.
    //        if (mf.hasAnnotation(NotSaved.class)) {
    //            return;
    //        }
    //
    //        // get the annotation from the field.
    //        Class<? extends Annotation> annType = getFieldAnnotation(mf);
    //
    //        if (Property.class.equals(annType)
    //                || DbPrimaryKey.class.equals(annType)
    //                || DbPersistent.class.equals(annType)
    //                || Serialized.class.equals(annType)
    //                || mf.isTypeMongoCompatible()
    //                || (getConverters().hasSimpleValueConverter(mf)
    //                        ||
    // (getConverters().hasSimpleValueConverter(mf.getFieldValue(entity))))) {
    //            opts.getValueMapper().toDBObject(entity, mf, dbObject, involvedObjects, this);
    //        } else if (Reference.class.equals(annType)
    //                || MorphiaReference.class == mf.getConcreteType()) {
    //            opts.getReferenceMapper().toDBObject(entity, mf, dbObject, involvedObjects, this);
    //        } else if (Embedded.class.equals(annType)
    //                || dev.morphia.annotations.Embedded.class.equals(annType)) {
    //            opts.getEmbeddedMapper().toDBObject(entity, mf, dbObject, involvedObjects, this);
    //        } else {
    //            if (LOG.isDebugEnabled()) {
    //                LOG.debug(
    //                        "No annotation was found, using default mapper "
    //                                + opts.getDefaultMapper()
    //                                + " for "
    //                                + mf);
    //            }
    //            opts.getDefaultMapper().toDBObject(entity, mf, dbObject, involvedObjects, this);
    //        }
    //    }

    //    @Override
    //    public MappedClass addMappedClass(final Class c) {
    //
    //        MappedClass mappedClass = mappedClasses.get(c.getName());
    //        if (mappedClass == null) {
    //            mappedClass = new MappedClass(c, this);
    //            return addMappedClass(mappedClass, false);
    //        }
    //        return mappedClass;
    //    }

    private CustomMapper createEmbeddedMapper() {
        MoCustomMapper m = new MoCustomMapper(getOptions().getEmbeddedMapper());
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.persistent");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.manager");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.con");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.registryName");
        m.getIgnoreName().add("de.mhus.lib.core.lang.MObject.nls");
        m.getIgnoreName().add("de.mhus.lib.core.MLog.log");
        return m;
    }

    private CustomMapper createReferenceMapper() {
        MoCustomMapper m = new MoCustomMapper(getOptions().getReferenceMapper());
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.persistent");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.manager");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.con");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.registryName");
        m.getIgnoreName().add("de.mhus.lib.core.lang.MObject.nls");
        m.getIgnoreName().add("de.mhus.lib.core.MLog.log");
        return m;
    }

    protected CustomMapper createValueMapper() {
        MoCustomMapper m = new MoCustomMapper(getOptions().getValueMapper());
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.persistent");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.manager");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.con");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.registryName");
        m.getIgnoreName().add("de.mhus.lib.core.lang.MObject.nls");
        m.getIgnoreName().add("de.mhus.lib.core.MLog.log");
        return m;
    }

    protected CustomMapper createCustomMapper() {
        MoCustomMapper m = new MoCustomMapper(getOptions().getDefaultMapper());
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.persistent");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.manager");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.con");
        m.getIgnoreName().add("de.mhus.lib.adb.DbComfortableObject.registryName");
        m.getIgnoreName().add("de.mhus.lib.core.lang.MObject.nls");
        m.getIgnoreName().add("de.mhus.lib.core.MLog.log");
        return m;
    }

    public class MoCustomMapper implements CustomMapper {

        private HashSet<String> ignoreName = new HashSet<>();
        private CustomMapper defaultMapper;

        public MoCustomMapper(CustomMapper defaultMapper) {
            this.defaultMapper = defaultMapper;
        }

        @Override
        public void fromDBObject(
                Datastore datastore,
                DBObject dbObject,
                MappedField mf,
                Object entity,
                EntityCache cache,
                Mapper mapper) {

            // inject momanager
            if (entity instanceof DbComfortableObject)
                ((DbComfortableObject) entity).doInit(moManager, null, true);

            if (ignoreName.contains(mf.getFullName())) return;

            defaultMapper.fromDBObject(datastore, dbObject, mf, entity, cache, mapper);
        }

        @Override
        public void toDBObject(
                Object entity,
                MappedField mf,
                DBObject dbObject,
                Map<Object, DBObject> involvedObjects,
                Mapper mapper) {

            if (ignoreName.contains(mf.getFullName())) return;

            defaultMapper.toDBObject(entity, mf, dbObject, involvedObjects, mapper);
        }

        public HashSet<String> getIgnoreName() {
            return ignoreName;
        }
    }
}
