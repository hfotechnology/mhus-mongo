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
 * Represents a GeoJSON LineString type. Will be persisted into the database according to <a
 * href="http://geojson.org/geojson-spec .html#id3">the specification</a>.
 *
 * <p>The factory for creating a LineString is the {@code GeoJson.lineString} method.
 *
 * @see dev.morphia.geo.GeoJson#lineString(Point...)
 */
public class LineString implements Geometry {
    private final List<Point> coordinates;

    @SuppressWarnings("UnusedDeclaration") // used by Morphia
    private LineString() {
        coordinates = new ArrayList<Point>();
    }

    LineString(final Point... points) {
        this.coordinates = Arrays.asList(points);
    }

    LineString(final List<Point> points) {
        coordinates = points;
    }

    @Override
    public List<Point> getCoordinates() {
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

        LineString that = (LineString) o;

        if (!coordinates.equals(that.coordinates)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "LineString{" + "coordinates=" + coordinates + '}';
    }

    @Override
    public com.mongodb.client.model.geojson.LineString convert() {
        return convert(null);
    }

    @Override
    public com.mongodb.client.model.geojson.LineString convert(
            final CoordinateReferenceSystem crs) {
        return new com.mongodb.client.model.geojson.LineString(
                crs != null ? crs.convert() : null, GeoJson.convertPoints(coordinates));
    }
}
