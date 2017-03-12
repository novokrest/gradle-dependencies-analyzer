package com.oneuse

import com.oneuse.repository.Repository
import com.oneuse.repository.WellKnownRepositories
import spock.lang.Specification

class RepositorySpec extends Specification {

    def 'check that junit versions are found'() {
        given: 'central maven repository'
            def repository = Repository.create(WellKnownRepositories.CENTRAL_MAVEN_REPOSITORY)

        when: 'requesting JUnit versions'
            def versions = repository.findArtifactVersions('junit', 'junit')

        then:
            versions.size() > 3
            versions.collect({ it.toString() }).containsAll(['4.10', '4.11', '4.12'])
    }

    def 'check that junit versions are found in local repository'() {
        given: 'central maven repository'
        def repository = Repository.create('file:///tmp/')

        when: 'requesting JUnit versions'
        def versions = repository.findArtifactVersions('junit', 'junit')

        then:
        versions.size() > 3
        versions.collect({ it.toString() }).containsAll(['4.10', '4.11', '4.12'])
    }
}
