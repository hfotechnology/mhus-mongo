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
package dev.morphia.logging;

import java.util.ArrayList;
import java.util.List;

import dev.morphia.logging.jdk.JDKLoggerFactory;

/**
 * Morphia's logging factory that can return either an slf4j LoggerFactory or a JDK LoggerFactory.
 */
public final class MorphiaLoggerFactory {
    private static final List<String> FACTORIES = new ArrayList<String>();
    private static LoggerFactory loggerFactory;

    static {
        FACTORIES.add("dev.morphia.logging.slf4j.SLF4JLoggerImplFactory");
        FACTORIES.add(JDKLoggerFactory.class.getName());
    }

    private MorphiaLoggerFactory() {}

    /**
     * Gets or creates a Logger for the given class.
     *
     * @param c the class to use for naming
     * @return the Logger
     */
    public static Logger get(final Class<?> c) {
        init();
        return loggerFactory.get(c);
    }

    private static synchronized void init() {
        if (loggerFactory == null) {
            chooseLoggerFactory();
        }
    }

    private static void chooseLoggerFactory() {
        for (final String f : FACTORIES) {
            loggerFactory = newInstance(f);
            if (loggerFactory != null) {
                loggerFactory
                        .get(MorphiaLoggerFactory.class)
                        .info("LoggerImplFactory set to " + loggerFactory.getClass().getName());
                return;
            }
        }
        throw new IllegalStateException("Cannot instantiate any MorphiaLoggerFactory");
    }

    private static LoggerFactory newInstance(final String f) {
        try {
            return (LoggerFactory) Class.forName(f).newInstance();
        } catch (Throwable ignore) {
            return null;
        }
    }

    /**
     * Register a LoggerFactory; last one registered is used.
     *
     * @param factoryClass the factory class
     */
    public static void registerLogger(final Class<? extends LoggerFactory> factoryClass) {
        if (loggerFactory == null) {
            FACTORIES.add(0, factoryClass.getName());
        } else {
            throw new IllegalStateException(
                    "LoggerImplFactory must be registered before logging is initialized.");
        }
    }

    /** Clears the logger factory */
    public static void reset() {
        loggerFactory = null;
    }
}
