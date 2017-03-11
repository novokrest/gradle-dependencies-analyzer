package com.oneuse

import com.oneuse.repository.ArtifactResolver
import com.oneuse.repository.WellKnownRepositories
import spock.lang.Specification

class ArtifactResolverSpec extends Specification {
    def 'check that JUnit latest version are found'() {
        given: 'JUnit latest version in central maven repository'
            def junitLatestVersion = '4.12'
            def artifactResolver = ArtifactResolver.create(WellKnownRepositories.CENTRAL_MAVEN_REPOSITORY)

        when: 'find JUnit latest version'
            def foundVersion = artifactResolver.findLatestVersion('junit', 'junit')

        then:
            foundVersion == junitLatestVersion
    }
}
