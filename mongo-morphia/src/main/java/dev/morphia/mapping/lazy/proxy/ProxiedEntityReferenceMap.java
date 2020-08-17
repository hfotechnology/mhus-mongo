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
package dev.morphia.mapping.lazy.proxy;

import java.util.Map;

import dev.morphia.Key;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public interface ProxiedEntityReferenceMap extends ProxiedReference {
    /** @return the reference map */
    // CHECKSTYLE:OFF
    Map<Object, Key<?>> __getReferenceMap();
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF
    void __put(Object key, Key<?> referenceKey);
    // CHECKSTYLE:ON
}
