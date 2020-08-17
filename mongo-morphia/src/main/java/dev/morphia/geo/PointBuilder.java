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

/**
 * Creates Point instances representing a <a
 * href="http://docs.mongodb.org/manual/apps/geospatial-indexes/#geojson-objects">GeoJSON</a> point
 * type. The advantage of using the builder is to reduce confusion of the order of the latitude and
 * longitude double values.
 *
 * <p>Supported by server versions 2.4 and above.
 *
 * @see dev.morphia.geo.Point
 */
public class PointBuilder {
    private double longitude;
    private double latitude;

    /**
     * Convenience method to return a new PointBuilder.
     *
     * @return a new instance of PointBuilder.
     */
    public static PointBuilder pointBuilder() {
        return new PointBuilder();
    }

    /**
     * Creates an immutable point
     *
     * @return the Point with the specifications from this builder.
     */
    public Point build() {
        return new Point(latitude, longitude);
    }

    /**
     * Add a latitude.
     *
     * @param latitude the latitude of the point
     * @return this PointBuilder
     */
    public PointBuilder latitude(final double latitude) {
        this.latitude = latitude;
        return this;
    }

    /**
     * Add a longitude.
     *
     * @param longitude the longitude of the point
     * @return this PointBuilder
     */
    public PointBuilder longitude(final double longitude) {
        this.longitude = longitude;
        return this;
    }
}
