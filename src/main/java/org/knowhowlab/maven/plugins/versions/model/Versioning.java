package org.knowhowlab.maven.plugins.versions.model;

public class Versioning {

    private VersionInformation vi;

    private String version;

    public Versioning(String version) {
        this.version = version;
        this.vi = new VersionInformation(version);
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
