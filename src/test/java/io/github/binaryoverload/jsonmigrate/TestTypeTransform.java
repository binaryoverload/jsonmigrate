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

import io.github.binaryoverload.JSONConfig;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestTypeTransform extends JsonMigration {

    public TestTypeTransform() {
        super("testtypetransform", new JSONConfig(JSONConfig.class.getClassLoader().getResourceAsStream("test.json")));
    }

    @Override
    public void migrate() {
        changeType("date", getConfig().getInteger("date").getAsInt(), new StringToIntTransformation());
    }

    @Override
    public void reverseMigration() {
        reverseChangeType("date", getConfig().getString("date").get(), new StringToIntTransformation());
    }

    @Test
    public void test() {
        assertTrue(getConfig().getElement("date").get().isJsonPrimitive());
        assertTrue(getConfig().getElement("date").get().getAsJsonPrimitive().isNumber());

        migrate();

        assertTrue(getConfig().getElement("date").get().isJsonPrimitive());
        assertTrue(getConfig().getElement("date").get().getAsJsonPrimitive().isString());

        reverseMigration();

        assertTrue(getConfig().getElement("date").get().isJsonPrimitive());
        assertTrue(getConfig().getElement("date").get().getAsJsonPrimitive().isNumber());
    }

    public class StringToIntTransformation extends ClassTransformation<Integer, String> {

        @Override
        public String transform(Integer t1) {
            return String.valueOf(t1);
        }

        @Override
        public Integer reverseTransform(String t2) {
            return Integer.parseInt(t2);
        }
    }

}
