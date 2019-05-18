/*
 * MIT License
 *
 * Copyright (c) 2019 Know-How Lab
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

package org.knowhowlab.maven.plugins.versions.model;

public class VersionInfo {

    private VersionParser vi;

    private String version;

    public VersionInfo(String version) {
        this.version = version;
        this.vi = new VersionParser(version);
    }

    public String getVersion() {
        return this.version;
    }

    public int getMajor() {
        return this.vi.getMajor();
    }

    public int getMinor() {
        return this.vi.getMinor();
    }

    public int getPatch() {
        return this.vi.getPatch();
    }

    public long getBuildNumber() {
        return this.vi.getBuildNumber();
    }

    public String getQualifier() {
        return this.vi.getQualifier();
    }
}
