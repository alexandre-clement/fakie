package com.fakie.io.input.codesmell;

import com.fakie.io.input.FakieInputException;
import com.fakie.model.processor.CodeSmell;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class PaprikaDetectionParserTest {
    @Test
    public void parseBlobCSV() throws URISyntaxException, FakieInputException {
        URL dir = getClass().getClassLoader().getResource("codesmell");
        assert dir != null : "Directory \'codesmell\' could not be located";
        URI uri = dir.toURI();
        File resource = new File(uri);
        PaprikaDetectionParser paprikaDetectionParser = new PaprikaDetectionParser();
        List<CodeSmell> codeSmells = paprikaDetectionParser.parse(resource);
        assertEquals(139, codeSmells.size());
    }
}