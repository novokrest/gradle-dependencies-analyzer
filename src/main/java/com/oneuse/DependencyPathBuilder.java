package com.oneuse;

import java.util.LinkedList;

public class DependencyPathBuilder {
    private final LinkedList<ArtifactName> artifactNames = new LinkedList<>();

    private DependencyPathBuilder() { }

    public static DependencyPathBuilder create() {
        return new DependencyPathBuilder();
    }

    public DependencyPathBuilder add(ArtifactName name) {
        artifactNames.add(name);
        return this;
    }

    public DependencyPathBuilder add(Iterable<ArtifactName> artifactNames) {
        artifactNames.forEach(artifactName -> add(artifactName));
        return this;
    }

    public DependencyPathBuilder removeLast() {
        artifactNames.removeLast();
        return this;
    }

    public DependencyPath build() {
        return new DependencyPath(artifactNames);
    }

}
