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

package org.knowhowlab.maven.plugins.versions;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.knowhowlab.maven.plugins.versions.model.VersionInfo;

import java.util.Map;

@Mojo(name = "parse", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true)
public class ParseMojo extends AbstractMojo {
    @Parameter(required = true)
    private Map<String, String> versions;

    @Parameter(defaultValue = "false")
    private boolean setVersionParts;

    @Parameter(defaultValue = "true")
    private boolean setVersions;

    @Parameter(readonly = true, defaultValue = "${project}")
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        for (Map.Entry<String, String> entry : versions.entrySet()) {
            parseVersion(entry.getKey(), entry.getValue());
        }
    }

    private void parseVersion(String property, String version) {
        VersionInfo artifactVersion = new VersionInfo(version);

        getLog().debug("Parsed Version for: " + property);
        getLog().debug("         major: " + artifactVersion.getMajor());
        getLog().debug("         minor: " + artifactVersion.getMinor());
        getLog().debug("   incremental: " + artifactVersion.getPatch());
        getLog().debug("   buildnumber: " + artifactVersion.getBuildNumber());
        getLog().debug("     qualifier: " + artifactVersion.getQualifier());

        if (setVersionParts) {
            defineVersionPartProperty(property, "majorVersionPart", artifactVersion.getMajor());
            defineVersionPartProperty(property, "minorVersionPart", artifactVersion.getMinor());
            defineVersionPartProperty(property, "incrementalVersionPart", artifactVersion.getPatch());
            defineVersionPartProperty(property, "buildNumberPart", artifactVersion.getBuildNumber());

            defineVersionPartProperty(property, "nextMajorVersionPart", artifactVersion.getMajor() + 1);
            defineVersionPartProperty(property, "nextMinorVersionPart", artifactVersion.getMinor() + 1);
            defineVersionPartProperty(property, "nextIncrementalVersionPart", artifactVersion.getPatch() + 1);
            defineVersionPartProperty(property, "nextBuildNumberPart", artifactVersion.getBuildNumber() + 1);
        }

        if (setVersions) {
            defineVersionProperty(property, "majorVersion", artifactVersion.getMajor());
            defineVersionProperty(property, "minorVersion", artifactVersion.getMajor(), artifactVersion.getMinor());
            defineVersionProperty(property, "incrementalVersion", artifactVersion.getMajor(), artifactVersion.getMinor(), artifactVersion.getPatch());
            defineVersionProperty(property, "buildNumberVersion", artifactVersion.getMajor(), artifactVersion.getMinor(), artifactVersion.getPatch(), artifactVersion.getBuildNumber());

            defineVersionProperty(property, "nextMajorVersion", artifactVersion.getMajor() + 1);
            defineVersionProperty(property, "nextMinorVersion", artifactVersion.getMajor(), artifactVersion.getMinor() + 1);
            defineVersionProperty(property, "nextIncrementalVersion", artifactVersion.getMajor(), artifactVersion.getMinor(), artifactVersion.getPatch() + 1);
            defineVersionProperty(property, "nextBuildNumberVersion", artifactVersion.getMajor(), artifactVersion.getMinor(), artifactVersion.getPatch(), artifactVersion.getBuildNumber() + 1);
        }
    }

    private void defineVersionPartProperty(String propertyPrefix, String name, String value) {
        defineProperty(propertyPrefix + '.' + name, value);
    }

    private void defineVersionPartProperty(String propertyPrefix, String name, int value) {
        defineVersionPartProperty(propertyPrefix, name, Integer.toString(value));
    }

    private void defineVersionPartProperty(String propertyPrefix, String name, long value) {
        defineVersionPartProperty(propertyPrefix, name, Long.toString(value));
    }

    private void defineVersionProperty(String propertyPrefix, String name, int major) {
        defineProperty(propertyPrefix + "." + name, String.format("%d", major));
    }

    private void defineVersionProperty(String propertyPrefix, String name, int major, int minor) {
        defineProperty(propertyPrefix + "." + name, String.format("%d.%d", major, minor));
    }

    private void defineVersionProperty(String propertyPrefix, String name, int major, int minor, int incremental) {
        defineProperty(propertyPrefix + "." + name, String.format("%d.%d.%d", major, minor, incremental));
    }

    private void defineVersionProperty(String propertyPrefix, String name, int major, int minor, int incremental, long build) {
        defineProperty(propertyPrefix + "." + name, String.format("%d.%d.%d.%d", major, minor, incremental, build));
    }

    private void defineProperty(String name, String value) {
        if (getLog().isDebugEnabled()) {
            getLog().debug("define property " + name + " = \"" + value + "\"");
        }

        project.getProperties().put(name, value);
    }

}
