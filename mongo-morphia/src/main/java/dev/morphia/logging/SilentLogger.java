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

/** Silent logger; it doesn't do anything! */
public class SilentLogger implements Logger {
    @Override
    public void debug(final String msg) {}

    @Override
    public void debug(final String format, final Object... arg) {}

    @Override
    public void debug(final String msg, final Throwable t) {}

    @Override
    public void error(final String msg) {}

    @Override
    public void error(final String format, final Object... arg) {}

    @Override
    public void error(final String msg, final Throwable t) {}

    @Override
    public void info(final String msg) {}

    @Override
    public void info(final String format, final Object... arg) {}

    @Override
    public void info(final String msg, final Throwable t) {}

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isWarningEnabled() {
        return false;
    }

    @Override
    public void trace(final String msg) {}

    @Override
    public void trace(final String format, final Object... arg) {}

    @Override
    public void trace(final String msg, final Throwable t) {}

    @Override
    public void warning(final String msg) {}

    @Override
    public void warning(final String format, final Object... arg) {}

    @Override
    public void warning(final String msg, final Throwable t) {}
}
