package com.oneuse;

import com.oneuse.exceptions.DependencyNotFoundException;

public class DependencyPathResolver {
    private final Artifact artifact;
    private final ArtifactName foundDependencyName;

    public interface ResolveResult {
        boolean isResolved();
        DependencyPath getDependencyPath();
    }

    public DependencyPathResolver(Artifact artifact, ArtifactName foundDependency) {
        this.artifact = artifact;
        this.foundDependencyName = foundDependency;
    }

    public ResolveResult resolveDependency() {
        DependencyPathBuilder dependencyPathBuilder = DependencyPathBuilder.create();

        return resolveDependency(dependencyPathBuilder, artifact)
                ? successfullResolveResult(dependencyPathBuilder.build())
                : failedResolveResult();
    }

    private boolean resolveDependency(DependencyPathBuilder dependencyPathBuilder, Artifact artifact) {
        if (artifact.getName().equals(foundDependencyName)) {
            dependencyPathBuilder.add(foundDependencyName);
            return true;
        }

        dependencyPathBuilder.add(artifact.getName());
        for (Artifact dependency: artifact.getDirectDependencies()) {
            if (resolveDependency(dependencyPathBuilder, dependency)) {
                return true;
            }
        }
        dependencyPathBuilder.removeLast();

        return false;
    }

    private static ResolveResult successfullResolveResult(final DependencyPath dependencyPath) {
        return new ResolveResult() {
            @Override
            public boolean isResolved() {
                return true;
            }

            @Override
            public DependencyPath getDependencyPath() {
                return dependencyPath;
            }
        };
    }

    private ResolveResult failedResolveResult() {
        return new ResolveResult() {
            @Override
            public boolean isResolved() {
                return false;
            }

            @Override
            public DependencyPath getDependencyPath() {
                throw new DependencyNotFoundException(foundDependencyName);
            }
        };
    }
}
