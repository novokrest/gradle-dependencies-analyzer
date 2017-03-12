package com.oneuse.repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArtifactVersionProvider {
    private final Repository repository;

    public static ArtifactVersionProvider create(String repositoryUrl) {
        return new ArtifactVersionProvider(Repository.create(repositoryUrl));
    }

    public ArtifactVersionProvider(Repository repository) {
        this.repository = repository;
    }

    public List<String> findVersions(String groupId, String artifactId) {
        return repository.findArtifactVersions(groupId, artifactId)
                .stream()
                .map(version -> version.toString())
                .collect(Collectors.toList());
    }

    public String findLatestVersion(String groupId, String artifactId) {
        return repository.findArtifactVersions(groupId, artifactId)
                .stream()
                .max(Comparator.naturalOrder())
                .get()
                .toString();
    }
}
