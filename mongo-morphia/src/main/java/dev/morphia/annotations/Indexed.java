/**
 * Copyright (c) 2008-2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.morphia.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.morphia.utils.IndexDirection;


/**
 * Specified on fields that should be Indexed.
 *
 * @author Scott Hernandez
 */
@SuppressWarnings("deprecation")
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Indexed {
    /**
     * @return Options to apply to the index. Use of this field will ignore any of the deprecated options defined on {@link Index} directly.
     */
    IndexOptions options() default @IndexOptions();

    /**
     * @return if true, creates the index in the background if true
     *
     *  use the {@link IndexOptions} found in {@link #options()}
     */
    
    boolean background() default false;

    /**
     * @return if true, tells the unique index to drop duplicates silently when creating; only the first will be kept
     *
     *  Support for this has been removed from the server.  This value is ignored.
     */
    
    boolean dropDups() default false;

    /**
     * @return the time to live for documents in the collection
     *
     *  use the {@link IndexOptions} found in {@link #options()}
     */
    
    int expireAfterSeconds() default -1;

    /**
     * @return The name of the index to create; default is to let the mongodb create a name (in the form of key1_1/-1_key2_1/-1...)
     *
     *  use the {@link IndexOptions} found in {@link #options()}
     */
    
    String name() default "";

    /**
     * @return if true, create the index with the sparse option
     *
     *  use the {@link IndexOptions} found in {@link #options()}
     */
    
    boolean sparse() default false;

    /**
     * @return if true, creates the index as a unique value index; inserting duplicates values in this field will cause errors
     *
     *  use the {@link IndexOptions} found in {@link #options()}
     */
    
    boolean unique() default false;

    /**
     * @return the type of the index (ascending, descending, geo2d); default is ascending
     * @see IndexDirection
     */
    IndexDirection value() default IndexDirection.ASC;
}
