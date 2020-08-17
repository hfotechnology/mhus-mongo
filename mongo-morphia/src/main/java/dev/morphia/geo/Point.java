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
package dev.morphia.geo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.model.geojson.Position;

import dev.morphia.annotations.Embedded;

/**
 * Represents a GeoJSON Point type. Will be persisted into the database according to <a
 * href="http://geojson.org/geojson-spec.html#id2">the specification</a>. Therefore because of this,
 * this entity will never have its own ID or store the its Class name.
 *
 * <p>The builder for creating a Point is the {@code GeoJson.pointBuilder} method, or the helper
 * {@code GeoJson.point} factory method.
 *
 * @see dev.morphia.geo.GeoJson#point(double, double)
 */
@Embedded
public class Point implements Geometry {
    private final List<Double> coordinates = new ArrayList<Double>();

    Point(final double latitude, final double longitude) {
        coordinates.add(longitude);
        coordinates.add(latitude);
    }

    Point(final List<Double> coordinates) {
        this.coordinates.addAll(coordinates);
    }

    @Override
    public List<Double> getCoordinates() {
        return coordinates;
    }

    /**
     * Return the latitude of this point.
     *
     * @return the Point's latitude
     */
    public double getLatitude() {
        return coordinates.get(1);
    }

    /**
     * Return the longitude of this point.
     *
     * @return the Point's longitude
     */
    public double getLongitude() {
        return coordinates.get(0);
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

        Point point = (Point) o;

        if (getCoordinates().size() != point.getCoordinates().size()) {
            return false;
        }
        for (int i = 0; i < coordinates.size(); i++) {
            final Double coordinate = coordinates.get(i);
            if (Double.compare(coordinate, point.getCoordinates().get(i)) != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("Point{coordinates=%s}", coordinates);
    }

    /**
     * @morphia.internal
     * @return this Point converted to a driver Point
     */
    @Override
    public com.mongodb.client.model.geojson.Point convert() {
        return convert(null);
    }

    /**
     * @morphia.internal
     * @return this Point converted to a driver Point
     */
    @Override
    public com.mongodb.client.model.geojson.Point convert(final CoordinateReferenceSystem crs) {
        return new com.mongodb.client.model.geojson.Point(
                crs != null ? crs.convert() : null, new Position(getLongitude(), getLatitude()));
    }
}
