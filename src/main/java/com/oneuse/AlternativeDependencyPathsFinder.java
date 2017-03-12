package com.oneuse;

import com.oneuse.repository.ArtifactVersionProvider;
import com.oneuse.repository.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class AlternativeDependencyPathsFinder {
    private static final String INCONSISTENT_ARGUMENTS_ERROR_FORMAT = "Inconsistent dependency path! " +
            "                                                          Expected dependency path root: '%s', actual: '%s'";

    private final Repository repository;
    private final ArtifactName rootArtifactName;
    private final DependencyPath originalDependencyPath;

    public AlternativeDependencyPathsFinder(Repository repository, ArtifactName artifactName, DependencyPath originalDependencyPath) {
        if (!originalDependencyPath.root().equals(artifactName)) {
            throw new IllegalArgumentException(String.format(INCONSISTENT_ARGUMENTS_ERROR_FORMAT,
                                                             artifactName, originalDependencyPath.root()));
        }

        this.repository = repository;
        this.rootArtifactName = artifactName;
        this.originalDependencyPath = originalDependencyPath;
    }

    public List<DependencyPath> findAlternativePaths() {
        return findAlternativeFirstLevelDependencies()
                .stream()
                .map(firstLevelDependency -> resolveTargetDependency(firstLevelDependency))
                .filter(resolveResult -> resolveResult.isResolved())
                .map(resolveResult -> resolveResult.getDependencyPath())
                .map(dependencyPath -> createFullDependencyPath(dependencyPath))
                .collect(Collectors.toList());
    }

    private List<Artifact> findAlternativeFirstLevelDependencies() {
        ArtifactName firstLevelDependencyName = getFirstLevelDependencyName();
        ArtifactVersionProvider versionProvider = new ArtifactVersionProvider(repository);
        return versionProvider.findVersions(firstLevelDependencyName.getGroup(), firstLevelDependencyName.getName())
                              .stream()
                              .filter(version -> !version.equals(firstLevelDependencyName.getVersion()))
                              .map(version -> createFirstLevelDependency(version))
                              .collect(Collectors.toList());
    }

    private ArtifactName getFirstLevelDependencyName() {
        return originalDependencyPath.getDependencyAt(1);
    }

    private Artifact createFirstLevelDependency(String alternativeVersion) {
        return new Artifact(repository, getFirstLevelDependencyName().changeVersion(alternativeVersion));
    }

    private DependencyPathResolver.ResolveResult resolveTargetDependency(Artifact artifact) {
        DependencyPathResolver resolver = new DependencyPathResolver(artifact, getTargetDependencyName());
        return resolver.resolveDependency();
    }

    private ArtifactName getTargetDependencyName() {
        return originalDependencyPath.targetDependency();
    }

    private DependencyPath createFullDependencyPath(DependencyPath fromFirstLevelDependencyPath) {
        return DependencyPathBuilder.create()
                .add(rootArtifactName)
                .add(fromFirstLevelDependencyPath)
                .build();

    }
}
