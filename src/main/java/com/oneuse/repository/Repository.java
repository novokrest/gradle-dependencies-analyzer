package com.oneuse.repository;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.version.Version;

import java.util.Arrays;
import java.util.List;

public class Repository {
    private static final String ALL_ARTIFACT_VERSIONS_FORMAT = "%s:%s:[0,)";
    private final RepositorySystem repositorySystem;
    private final RepositorySystemSession session;
    private final RemoteRepository remoteRepository;

    public static Repository create(String repositoryUrl) {
        RepositorySystem repositorySystem = RepositoryService.newRepositorySystem();
        RepositorySystemSession session = RepositoryService.newRepositorySystemSession(repositorySystem);
        RemoteRepository remoteRepository = new RemoteRepository.Builder(null, "default", repositoryUrl).build();

        return new Repository(repositorySystem, session, remoteRepository);
    }

    private Repository(RepositorySystem repositorySystem,
                       RepositorySystemSession session,
                       RemoteRepository remoteRepository) {
        this.repositorySystem = repositorySystem;
        this.session = session;
        this.remoteRepository = remoteRepository;
    }

    public List<Version> findArtifactVersions(String groupId, String artifactId) {
        VersionRangeRequest request = createVersionRangeRequest(groupId, artifactId);
        VersionRangeResult versionRangeResult = resolveVersionRange(request);

        return versionRangeResult.getVersions();
    }

    private VersionRangeRequest createVersionRangeRequest(String groupId, String artifactId) {
        Artifact artifact = new DefaultArtifact(String.format(ALL_ARTIFACT_VERSIONS_FORMAT, groupId, artifactId));
        VersionRangeRequest rangeRequest = new VersionRangeRequest();

        rangeRequest.setArtifact(artifact);
        rangeRequest.setRepositories(Arrays.asList(remoteRepository));

        return rangeRequest;
    }

    private VersionRangeResult resolveVersionRange(VersionRangeRequest request) {
        try {
            return repositorySystem.resolveVersionRange(session, request);
        } catch (VersionRangeResolutionException e) {
            throw new RuntimeException(e);
        }
    }
}
