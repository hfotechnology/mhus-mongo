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

import static java.lang.String.format;

import com.mongodb.WriteResult;

/** This class holds various metrics about the results of an update operation. */
public class UpdateResults {
    private final WriteResult wr;

    /**
     * Creates an UpdateResults
     *
     * @param wr the WriteResult from the driver.
     */
    public UpdateResults(final WriteResult wr) {
        this.wr = wr;
    }

    /** @return number inserted; this should be either 0/1. */
    public int getInsertedCount() {
        return !getUpdatedExisting() ? getN() : 0;
    }

    /** @return the new _id field if an insert/upsert was performed */
    public Object getNewId() {
        return wr.getUpsertedId();
    }

    /** @return number updated */
    public int getUpdatedCount() {
        return getUpdatedExisting() ? getN() : 0;
    }

    /** @return true if updated, false if inserted or none effected */
    public boolean getUpdatedExisting() {
        return wr.isUpdateOfExisting();
    }

    /** @return the underlying data */
    public WriteResult getWriteResult() {
        return wr;
    }

    /** @return number of affected documents */
    protected int getN() {
        return wr.getN();
    }

    @Override
    public String toString() {
        return format("UpdateResults{wr=%s}", wr);
    }
}
