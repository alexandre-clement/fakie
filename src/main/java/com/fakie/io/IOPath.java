package com.fakie.io;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum IOPath {
    GRAPH_DIRECTORY("dataset"),
    GRAPH_FILENAME("graph.fakie"),
    QUERY_FOLDER("queries"),
    CYPHER_FOLDER(QUERY_FOLDER.path.resolve("cypher"));

    private final String string;
    private final Path path;
    private final File file;

    IOPath(String string) {
        this(Paths.get(string));
    }

    IOPath(Path path) {
        this.string = path.toString();
        this.path = path;
        this.file = path.toFile();
    }

    public File asFile() {
        return file;
    }

    public Path asPath() {
        return path;
    }

    public String asString() {
        return string;
    }
}
