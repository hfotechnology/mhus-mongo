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
package dev.morphia;

import com.mongodb.MapReduceCommand.OutputType;

/** Defines how the output of the map reduce job is handled. use {@link OutputType} instead */
public enum MapreduceType {
    REPLACE,
    MERGE,
    REDUCE,
    INLINE;

    /**
     * Finds the type represented by the value given
     *
     * @param value the value to look up
     * @return the type represented by the value given
     */
    public static MapreduceType fromString(final String value) {
        for (int i = 0; i < values().length; i++) {
            final MapreduceType fo = values()[i];
            if (fo.name().equals(value)) {
                return fo;
            }
        }
        return null;
    }

    OutputType toOutputType() {
        switch (this) {
            case REDUCE:
                return OutputType.REDUCE;
            case MERGE:
                return OutputType.MERGE;
            case INLINE:
                return OutputType.INLINE;
            default:
                return OutputType.REPLACE;
        }
    }
}
