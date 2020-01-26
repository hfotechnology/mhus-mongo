/**
 * Copyright (c) 2008-2015 MongoDB, Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.morphia.geo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;

/**
 * This class represents a collection of mixed GeoJson objects as per the <a
 * href="http://geojson.org/geojson-spec .html#geometrycollection">GeoJSON specification</a>.
 * Therefore this entity will never have its own ID or store the its Class name.
 *
 * <p>The factory for creating a MultiPoint is the {@code GeoJson.multiPoint} method.
 *
 * @see dev.morphia.geo.GeoJson
 */
@Embedded
@Entity(noClassnameStored = true)
public class GeometryCollection {
    private final String type = "GeometryCollection";
    private final List<Geometry> geometries;

    @SuppressWarnings("UnusedDeclaration") // needed by morphia
    private GeometryCollection() {
        geometries = new ArrayList<Geometry>();
    }

    GeometryCollection(final List<Geometry> geometries) {
        this.geometries = geometries;
    }

    GeometryCollection(final Geometry... geometries) {
        this.geometries = Arrays.asList(geometries);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + geometries.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeometryCollection that = (GeometryCollection) o;

        if (!geometries.equals(that.geometries)) {
            return false;
        }
        if (!type.equals(that.type)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "GeometryCollection{" + "type='" + type + '\'' + ", geometries=" + geometries + '}';
    }
}
