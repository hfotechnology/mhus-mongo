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

import java.util.List;

/**
 * Interface to denote which entities are classes that will serialise into a MongoDB GeoJson object.
 */
public interface Geometry {
    /**
     * Returns a list of coordinates for this Geometry type. For something like a Point, this will
     * be a pair of lat/long coordinates, but for more complex types this will be a list of other
     * Geometry objects. Used for serialisation to MongoDB.
     *
     * @return a List containing either Geometry objects, or a pair of coordinates as doubles
     */
    List<?> getCoordinates();

    /**
     * Converts this type to the driver type
     *
     * @return the driver type
     */
    com.mongodb.client.model.geojson.Geometry convert();

    /**
     * Converts this type to the driver type
     *
     * @param crs the CRS to use
     * @return the driver type
     */
    com.mongodb.client.model.geojson.Geometry convert(CoordinateReferenceSystem crs);
}
