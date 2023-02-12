package org.erc.netflix.export;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class ExportParserTest {
    
    @Test
    void testExport() throws IOException, ExportParserException{
        byte[] content = Files.readAllBytes(Paths.get("./src/test/resources/Netflix.html"));
        ExportParser parser = new ExportParser();
        String contentParsed = parser.parse(new String(content,StandardCharsets.UTF_8));
        assertNotNull(contentParsed);
        System.out.println(contentParsed);
    }
}
