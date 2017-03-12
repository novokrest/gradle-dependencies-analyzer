package com.oneuse

class TestRepositories {
    private TestRepositories() { }

    static String REPO_1 = repoUrlByName('case-1')

    private static String repoUrlByName(String repoName) {
        "file://${new File('.').absolutePath}/src/test/resources/${repoName}/maven-repo"
    }
}
