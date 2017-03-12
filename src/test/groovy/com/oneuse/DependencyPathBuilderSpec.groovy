package com.oneuse

import spock.lang.Specification

class DependencyPathBuilderSpec extends Specification {
    def 'fail to create empty path'() {
        given:
            def builder = DependencyPathBuilder.create()

        when:
            builder.build();

        then:
            thrown Exception
    }

    def 'fail to create dependency path with one node'() {
        given:
            def builder = DependencyPathBuilder.create()

        when:
            builder.add(ArtifactName.parse('test:test:1.0')).build()

        then:
            thrown Exception
    }

    def 'check that last added artifact name is removed successfully'() {
        given:
            def dependencyPath = DependencyPathBuilder
                                        .create()
                                        .add(ArtifactName.parse('test:artifact-1:1.0'))
                                        .add(ArtifactName.parse('test:artifact-2:2.0'))
                                        .add(ArtifactName.parse('test:artifact-3:3.0'))
                                        .removeLast()
                                        .build()
        expect:
            def dependencies = dependencyPath.collect({ it.toString() });
            dependencies.size() == 2 && dependencies.containsAll(['test:artifact-1:1.0', 'test:artifact-2:2.0'])
    }
}
