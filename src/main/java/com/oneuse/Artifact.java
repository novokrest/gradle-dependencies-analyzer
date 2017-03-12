package com.oneuse;

import com.oneuse.repository.Repository;
import org.eclipse.aether.graph.Dependency;

import java.util.List;
import java.util.stream.Collectors;

public class Artifact {
    private final Repository repository;
    private final ArtifactName name;

    public Artifact(Repository repository, ArtifactName name) {
        this.repository = repository;
        this.name = name;
    }

    public ArtifactName getName() {
        return name;
    }

    public List<Artifact> getDirectDependencies() {
        List<Dependency> dependencies = repository.findArtifactDirectDependencies(name);
        return dependencies.stream()
                           .map(dependency -> new Artifact(repository, extractArtifactName(dependency)))
                           .collect(Collectors.toList());
    }

    private static ArtifactName extractArtifactName(Dependency dependency) {
        org.eclipse.aether.artifact.Artifact artifact = dependency.getArtifact();
        return new ArtifactName(artifact.getGroupId(), artifact.getArtifactId(), artifact.getVersion());
    }
}
