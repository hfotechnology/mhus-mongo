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
package dev.morphia.query;

/** Defines the base Criteria implementation. */
public abstract class AbstractCriteria implements Criteria {
    private CriteriaContainer attachedTo;

    @Override
    public void attach(final CriteriaContainer container) {
        if (attachedTo != null) {
            attachedTo.remove(this);
        }

        attachedTo = container;
    }

    /** @return the CriteriaContainer this Criteria is attached to */
    public CriteriaContainer getAttachedTo() {
        return attachedTo;
    }

    /**
     * Sets the parents CriteriaContainer for this Criteria
     *
     * @param attachedTo the CriteriaContainer
     */
    public void setAttachedTo(final CriteriaContainer attachedTo) {
        this.attachedTo = attachedTo;
    }
}
