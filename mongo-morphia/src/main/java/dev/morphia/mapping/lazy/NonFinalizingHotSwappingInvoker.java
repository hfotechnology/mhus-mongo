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
package dev.morphia.mapping.lazy;

import java.lang.reflect.Method;

import com.thoughtworks.proxy.ProxyFactory;
import com.thoughtworks.proxy.kit.ObjectReference;
import com.thoughtworks.proxy.toys.delegate.DelegationMode;
import com.thoughtworks.proxy.toys.hotswap.HotSwappingInvoker;

import dev.morphia.annotations.IdGetter;
import dev.morphia.mapping.lazy.proxy.EntityObjectReference;

class NonFinalizingHotSwappingInvoker<T> extends HotSwappingInvoker<T> {

    private static final long serialVersionUID = 1L;

    NonFinalizingHotSwappingInvoker(
            final Class<?>[] types,
            final ProxyFactory proxyFactory,
            final ObjectReference<Object> delegateReference,
            final DelegationMode delegationMode) {
        super(types, proxyFactory, delegateReference, delegationMode);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args)
            throws Throwable {
        if ("finalize".equals(method.getName()) && args != null && args.length == 0) {
            return null;
        }

        /*
         * If the method being invoked is annotated with @IdGetter and the delegate reference is an EntityObjectReference,
         * return the id of the EntityObjectReference's key. This allows us to return the referenced entity's id without
         * fetching the entity from the datastore.
         */
        if (method.getAnnotation(IdGetter.class) != null) {
            ObjectReference<Object> delegateReference = getDelegateReference();
            if (delegateReference instanceof EntityObjectReference) {
                EntityObjectReference entityObjectReference =
                        (EntityObjectReference) delegateReference;
                return entityObjectReference.__getKey().getId();
            }
        }

        return super.invoke(proxy, method, args);
    }
}
