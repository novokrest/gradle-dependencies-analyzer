package com.oneuse;

import java.util.Iterator;
import java.util.LinkedList;

public class DependencyPath implements Iterable<ArtifactName> {
    private LinkedList<ArtifactName> dependencies;

    public DependencyPath(LinkedList<ArtifactName> dependencies) {
        if (dependencies.size() < 2) {
            throw new IllegalArgumentException("Unexpected dependency path length! " +
                                               "There are must be at least root artifact name and target dependency");
        }
        this.dependencies = dependencies;
    }

    public ArtifactName root() {
        return dependencies.getFirst();
    }

    public ArtifactName targetDependency() {
        return dependencies.getLast();
    }

    public ArtifactName getDependencyAt(int level) {
        return dependencies.get(level);
    }

    @Override
    public Iterator<ArtifactName> iterator() {
        return dependencies.iterator();
    }

    @Override
    public String toString() {
        return dependencies.stream()
                            .map(dependency -> dependency.toString())
                            .reduce((dep1, dep2) -> dep1 + " -> " + dep2)
                            .get();
    }
}
