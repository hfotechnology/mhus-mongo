/**
 * Copyright (c) 2008-2015 MongoDB, Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.morphia.mapping.lazy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Uwe Schaefer, (us@thomas-daily.de) */
public final class LazyFeatureDependencies {

    private static final Logger LOG = LoggerFactory.getLogger(LazyFeatureDependencies.class);
    private static Boolean fulFilled;

    private LazyFeatureDependencies() {}

    /**
     * Checks that the dependencies to support lazy proxies are present
     *
     * @return true the dependencies are found
     */
    public static boolean assertDependencyFullFilled() {
        final boolean fulfilled = testDependencyFullFilled();
        if (!fulfilled) {
            LOG.warn("Lazy loading impossible due to missing dependencies.");
        }
        return fulfilled;
    }

    /**
     * Checks that the dependencies to support lazy proxies are present
     *
     * @return true the dependencies are found
     */
    public static boolean testDependencyFullFilled() {
        if (fulFilled != null) {
            return fulFilled;
        }
        try {
            fulFilled =
                    Class.forName("net.sf.cglib.proxy.Enhancer") != null
                            && Class.forName("com.thoughtworks.proxy.toys.hotswap.HotSwapping")
                                    != null;
        } catch (ClassNotFoundException e) {
            fulFilled = false;
        }
        return fulFilled;
    }

    /**
     * Creates a LazyProxyFactory
     *
     * @return the LazyProxyFactory
     */
    public static LazyProxyFactory createDefaultProxyFactory() {
        if (testDependencyFullFilled()) {
            final String factoryClassName = "dev.morphia.mapping.lazy.CGLibLazyProxyFactory";
            try {
                return (LazyProxyFactory) Class.forName(factoryClassName).newInstance();
            } catch (Exception e) {
                LOG.error("While instantiating " + factoryClassName, e);
            }
        }
        return null;
    }
}
