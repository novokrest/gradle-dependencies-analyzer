package com.oneuse

import com.oneuse.repository.ArtifactVersionProvider
import com.oneuse.repository.WellKnownRepositories
import spock.lang.Specification

class ArtifactResolverSpec extends Specification {

    def 'check that JUnit latest version is found in central repository'() {
        given: 'JUnit latest version in central maven repository'
            def junitLatestVersion = '4.12'
            def artifactVersionProvider = ArtifactVersionProvider.create(WellKnownRepositories.CENTRAL_MAVEN_REPOSITORY)

        when: 'find JUnit latest version'
            def foundVersion = artifactVersionProvider.findLatestVersion('junit', 'junit')

        then:
            foundVersion == junitLatestVersion
    }

    def 'check that artifact latest version is found in test repository'() {
        println new File('.').absolutePath
        given: 'artifacts in local repository'
            def localRepositoryUrl = "file://${new File('.').absolutePath}/src/test/resources/case-1/maven-repo"
            def artifactLatestVersion = '1.2'
            def artifactVersionProvider = ArtifactVersionProvider.create(localRepositoryUrl)

        when: "try to find artifact's latest version"
            def foundVersion = artifactVersionProvider.findLatestVersion('test.dependency', 'alpha')

        then:
            foundVersion == artifactLatestVersion
    }
}
