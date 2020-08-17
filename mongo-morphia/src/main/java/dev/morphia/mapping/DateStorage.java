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
package dev.morphia.mapping;

import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;

import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.morphia.DatastoreImpl;

/** This enum is used to determine how Java 8 dates and times are stored in the database. */
@SuppressWarnings("Since15")
public enum DateStorage {
    UTC {
        @Override
        public ZoneId getZone() {
            return of("UTC");
        }
    },
    /**
     * This will be removed in 2.0. It is intended to bridge the gap when correcting the storage of
     * data/time values in the database. {@link #UTC} should be used and will be the default in 2.0.
     * In 1.5 it is {@link #SYSTEM_DEFAULT} for backwards compatibility.
     */
    SYSTEM_DEFAULT {
        @Override
        public ZoneId getZone() {
            if (!warningLogged) {
                warningLogged = true;
                LOG.warn(
                        "Currently using the system default zoneId for encoding.  This default will change in 2.0 to use UTC which will "
                                + "likely break your application.  Consult the migration guide for mitigation suggestions.");
            }

            return systemDefault();
        }
    };

    private static final Logger LOG = LoggerFactory.getLogger(DatastoreImpl.class);
    private static boolean warningLogged;

    /** @return the ZoneId for this storage type */
    public abstract ZoneId getZone();
}
