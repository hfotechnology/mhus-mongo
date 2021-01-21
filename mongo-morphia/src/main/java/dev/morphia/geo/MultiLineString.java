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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a series of lines, which will saved into MongoDB as per the <a
 * href="http://geojson.org/geojson-spec .html#id6">GeoJSON specification</a>.
 *
 * <p>The factory for creating a MultiLineString is the {@code GeoJson.multiLineString} method.
 *
 * @see dev.morphia.geo.GeoJson#multiLineString(LineString...)
 */
public class MultiLineString implements Geometry {
    private final List<LineString> coordinates;

    @SuppressWarnings("UnusedDeclaration") // needed for Morphia
    private MultiLineString() {
        this.coordinates = new ArrayList<LineString>();
    }

    MultiLineString(final LineString... lineStrings) {
        coordinates = Arrays.asList(lineStrings);
    }

    MultiLineString(final List<LineString> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public List<LineString> getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }

    /* equals, hashCode and toString. Useful primarily for testing and debugging. Don't forget to re-create when changing this class */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultiLineString that = (MultiLineString) o;

        if (!coordinates.equals(that.coordinates)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "MultiLineString{" + "coordinates=" + coordinates + '}';
    }

    @Override
    public com.mongodb.client.model.geojson.MultiLineString convert() {
        return convert(null);
    }

    @Override
    public com.mongodb.client.model.geojson.MultiLineString convert(
            final CoordinateReferenceSystem crs) {
        return new com.mongodb.client.model.geojson.MultiLineString(
                crs != null ? crs.convert() : null, GeoJson.convertLineStrings(coordinates));
    }
}
