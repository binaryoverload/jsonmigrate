/*
 * MIT License
 *
 * Copyright (c) 2018 William Oldham
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.binaryoverload.jsonmigrate;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.HashMap;
import java.util.Map;

public class LevelLogger {

    private static final Map<Level, LoggingFunction> map;

    static {
        map = new HashMap<>();
        map.put(Level.TRACE, (s, l) -> l.trace(s));
        map.put(Level.DEBUG, (s, l) -> l.debug(s));
        map.put(Level.INFO, (s, l) -> l.info(s));
        map.put(Level.WARN, (s, l) -> l.warn(s));
        map.put(Level.ERROR, (s, l) -> l.error(s));
    }

    public static void log(Level level, Logger l, String s) {
        map.get(level).log(s, l);
    }

    @FunctionalInterface
    private interface LoggingFunction {

        public void log(String arg, Logger l);
    }
}

