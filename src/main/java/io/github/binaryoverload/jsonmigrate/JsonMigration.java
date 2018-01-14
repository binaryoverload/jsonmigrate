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

public abstract class JsonMigration {

    private JSONConfig config;
    private String name;

    public JsonMigration(String name, JSONConfig config) {
        this.name = name;
        this.config = config;
    }

    public abstract void migrate() throws MigrationException;

    public void reverseMigration() throws MigrationException {
        throw new UnsupportedOperationException();
    }

    protected JSONConfig getConfig() {
        return config;
    }

    protected void movePath(String oldPath, String newPath) {
        if (!config.getElement(oldPath).isPresent()) {
            throw new IllegalStateException("The element to move does not exist!");
        }
        config.set(newPath, config.getElement(oldPath));
        config.remove(oldPath);
    }

    protected void copyPath(String oldPath, String newPath) {
        if (!config.getElement(oldPath).isPresent()) {
            throw new IllegalStateException("The element to copy does not exist!");
        }
        config.set(newPath, config.getElement(oldPath));
    }

    protected void deletePath(String path) {
        if (!config.getElement(path).isPresent()) {
            throw new IllegalStateException("The element to delete does not exist!");
        }
        config.remove(path);
    }



    protected <T1, T2> void changeType(String path, T1 t1, ClassTransformation<T1, T2> transformation) {
        getConfig().set(path, transformation.transform(t1));
    }

    protected <T1, T2> void reverseChangeType(String path, T2 t2, ClassTransformation<T1, T2> transformation) {
        getConfig().set(path, transformation.reverseTransform(t2));
    }

    public String getName() {
        return name;
    }
}
