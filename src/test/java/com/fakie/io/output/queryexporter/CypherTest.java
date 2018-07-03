package com.fakie.io.output.queryexporter;

import com.fakie.io.IOPath;
import com.fakie.io.output.FakieOutputException;
import com.fakie.learning.Rule;
import com.fakie.model.MockedGraph;
import com.fakie.utils.logic.*;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.predicate.Concatenator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.io.fs.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CypherTest {
    private Path path;

    @Before
    public void setUp() throws URISyntaxException {
        Path cypherPath = IOPath.CYPHER_FOLDER.asPath();
        URL url = getClass().getClassLoader().getResource(".");
        assert url != null : "Could not locate resources folder";
        path = new File(url.toURI()).toPath().resolve(cypherPath);
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteRecursively(path.toFile());
    }

    @Test
    public void createQueries() throws FakieOutputException, IOException {
        Cypher cypher = new Cypher();
        Operator left = new Or(Arrays.asList(
                new Expression("number_of_methods_greater_than_40", true),
                new Expression("number_of_methods_between_than_30_and_39", true)));
        Or right = new Or(Collections.singletonList(new Expression("blob", true)));
        Implication implication = new Implication(left, right);

        URL db = getClass().getClassLoader().getResource(".");
        assert db != null : "Could not locate the resources directory";
        Rule expectedRule = new Rule(implication, 0.9230769230769231);
        cypher.exportRulesAsQueries(path, Collections.singletonList(expectedRule));
        assertEquals(1, FileUtils.countFilesInDirectoryPath(path.resolve("blob")));
    }
}