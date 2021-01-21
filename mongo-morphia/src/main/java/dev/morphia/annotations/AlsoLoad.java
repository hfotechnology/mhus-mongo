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
package dev.morphia.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which helps migrate schemas by loading one of several possible properties in the
 * document into fields or methods. This is typically used when a field is renamed, allowing the
 * field to be populated by both its current name and any prior names.
 *
 * <p>When placed on a field, the additional names (document field) will be checked when this field
 * is loaded. If the document contains data for more than one of the names, an exception will be
 * thrown. (orig @author Jeff Schnitzer <jeff@infohazard.org> for Objectify)
 *
 * @author Scott Hernandez
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AlsoLoad {
    /**
     * @return An array of alternative fields to load should the primary field name be missing in a
     *     document.
     */
    String[] value();
}
