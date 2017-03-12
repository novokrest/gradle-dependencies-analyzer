package com.oneuse

import com.oneuse.repository.Repository
import com.oneuse.repository.WellKnownRepositories
import spock.lang.Specification

class DependencyPathResolverSpec extends Specification {
    def 'check that direct dependency is found successfully'() {
        given:
            def repository = Repository.create(WellKnownRepositories.CENTRAL_MAVEN_REPOSITORY)
            def artifactName = ArtifactName.parse('org.springframework.boot:spring-boot:1.5.2.RELEASE')
            def directDependencyName = ArtifactName.parse('org.springframework:spring-core:4.3.7.RELEASE')

        when: 'try to find dependency'
            def artifact = new Artifact(repository, artifactName)
            def resolver = new DependencyPathResolver(artifact, directDependencyName)
            def dependencyPath = resolver.resolveDependency().dependencyPath

        then: 'direct dependency is found'
            def dependencyNames = dependencyPath.collect({ it.toString() })
            dependencyNames.size() == 2 && dependencyNames == [artifactName.toString(), directDependencyName.toString()]
    }

    def 'check that transitive dependency is found successfully'() {
        given:
            def repository = Repository.create(WellKnownRepositories.CENTRAL_MAVEN_REPOSITORY)
            def artifactName = ArtifactName.parse('org.springframework.boot:spring-boot:1.5.2.RELEASE')
            def transitiveDependencyName = ArtifactName.parse('log4j:log4j:1.2.17')

        when: 'try to find dependency'
            def artifact = new Artifact(repository, artifactName)
            def dependencyPathResolver = new DependencyPathResolver(artifact, transitiveDependencyName)
            def dependencyPath = dependencyPathResolver.resolveDependency().dependencyPath

        then: 'transitive dependency is found'
            def dependencyNames = dependencyPath.collect({ it.toString() })
            dependencyNames.size() > 2 && dependencyNames.last() == transitiveDependencyName.toString()
    }

    def 'check that direct dependency is found successfully in test repository'() {
        given:
            def repository = Repository.create(TestRepositories.REPO_1)
            def artifactName = ArtifactName.parse('test.dependency:beta:1.1')
            def directDependencyName = ArtifactName.parse('test.dependency:alpha:1.1')

        when: 'try to find dependency'
            def artifact = new Artifact(repository, artifactName)
            def resolver = new DependencyPathResolver(artifact, directDependencyName)
            def dependencyPath = resolver.resolveDependency().dependencyPath

        then: 'direct dependency is found'
            def dependencyNames = dependencyPath.collect({ it.toString() })
            dependencyNames.size() == 2 && dependencyNames == [artifactName.toString(), directDependencyName.toString()]
    }

    def 'check that transitive dependency is found successfully in test repository'() {
        given:
            def repository = Repository.create(TestRepositories.REPO_1)
            def artifactName = ArtifactName.parse('test.dependency:zeta:1.1')
            def transitiveDependencyName = ArtifactName.parse('test.dependency:alpha:1.1')

        when: 'try to find dependency'
            def artifact = new Artifact(repository, artifactName)
            def dependencyPathResolver = new DependencyPathResolver(artifact, transitiveDependencyName)
            def dependencyPath = dependencyPathResolver.resolveDependency().dependencyPath

        then: 'transitive dependency is found'
            def dependencyNames = dependencyPath.collect({ it.toString() })
            dependencyNames.size() > 2 && dependencyNames.last() == transitiveDependencyName.toString()
    }
}
