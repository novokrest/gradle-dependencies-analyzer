package com.oneuse

import com.oneuse.repository.Repository
import spock.lang.Specification

class RepositorySpec extends Specification {
    def 'check that junit versions are found'() {
        given: 'central maven repository'
            def repository = Repository.create("http://central.maven.org/maven2/")

        when: 'requesting JUnit versions'
            def versions = repository.findArtifactVersions('junit', 'junit')

        then:
            versions.size() > 3
            versions.containsAll(['4.10', '4.11', '4.12'])
    }
}
