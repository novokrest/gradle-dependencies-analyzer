package com.oneuse;

public final class ArtifactName {
    private final static String ARTIFACT_NAME_FORMAT = "%s:%s:%s";

    private final String group;
    private final String name;
    private final String version;

    public static ArtifactName parse(String artifactName) {
        String[] parts = artifactName.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Incorrect artifact name format: expect 'groupId':'artifactId':'version'");
        }
        return new ArtifactName(parts[0], parts[1], parts[2]);
    }

    public ArtifactName(String group, String name, String version) {
        this.group = group;
        this.name = name;
        this.version = version;
    }

    public ArtifactName changeVersion(String version) {
        return new ArtifactName(group, name, version);
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if ((obj == null) || !(obj instanceof ArtifactName)) return false;

        ArtifactName other = (ArtifactName) obj;
        return other.group.equals(group)
                && other.name.equals(name)
                && other.version.equals(version);
    }

    @Override
    public String toString() {
        return String.format(ARTIFACT_NAME_FORMAT, group, name, version);
    }
}
