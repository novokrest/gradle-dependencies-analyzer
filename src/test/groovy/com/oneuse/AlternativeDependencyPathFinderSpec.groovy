package com.oneuse

import com.oneuse.repository.Repository
import com.oneuse.repository.WellKnownRepositories
import spock.lang.Specification

class AlternativeDependencyPathFinderSpec extends Specification {

    def 'check that alternative dependency paths are found successfully'() {
        given:
            def repository = Repository.create(WellKnownRepositories.CENTRAL_MAVEN_REPOSITORY)
            def artifactName = ArtifactName.parse('org.springframework.boot:spring-boot:1.5.2.RELEASE')
            def transitiveDependencyName = ArtifactName.parse('log4j:log4j:1.2.17')

            def artifact = new Artifact(repository, artifactName)
            def dependencyResolver = new DependencyPathResolver(artifact, transitiveDependencyName)
            def originalDependencyPath = dependencyResolver.resolveDependency().dependencyPath

        when:
            def finder = new AlternativeDependencyPathsFinder(repository, artifactName, originalDependencyPath)
            def alternativeDependencyPaths = finder.findAlternativePaths()

        then:
            alternativeDependencyPaths.size() > 0
            println alternativeDependencyPaths
    }
}
