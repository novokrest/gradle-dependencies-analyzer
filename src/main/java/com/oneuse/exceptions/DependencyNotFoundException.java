package com.oneuse.exceptions;

import com.oneuse.ArtifactName;

public class DependencyNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE_FORMAT = "Dependency '%s' is not found";

    public DependencyNotFoundException(ArtifactName dependency) {
        super(String.format(ERROR_MESSAGE_FORMAT, dependency));
    }
}
