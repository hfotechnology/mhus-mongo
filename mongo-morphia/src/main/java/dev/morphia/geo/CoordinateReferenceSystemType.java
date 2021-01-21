/**
 * Copyright (C) 2019 Mike Hummel (mh@mhus.de)
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
package dev.morphia.geo;

/** An enumeration of the GeoJSON coordinate reference system types. */
public enum CoordinateReferenceSystemType {
    /** A coordinate reference system that is specified by name */
    NAME("name"),

    /** A coordinate reference system that is specified by a dereferenceable URI */
    LINK("link");

    private final String typeName;

    CoordinateReferenceSystemType(final String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets the GeoJSON-defined name for the type.
     *
     * @return the GeoJSON-defined type name
     */
    public String getTypeName() {
        return typeName;
    }
}
