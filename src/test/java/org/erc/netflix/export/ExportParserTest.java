package org.erc.netflix.export;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.erc.netflix.export.struct.Netflix;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExportParserTest {
    
    @Test
    void testExport() throws IOException, ExportParserException{
        byte[] content = Files.readAllBytes(Paths.get("./src/test/resources/Netflix.html"));
        ExportParser parser = new ExportParser();
        String contentParsed = parser.parse(new String(content,StandardCharsets.UTF_8));
        assertNotNull(contentParsed);
        System.out.println(contentParsed);
    }

    @Test
    void testExport2() throws IOException, ExportParserException{
        byte[] content = Files.readAllBytes(Paths.get("./src/test/resources/Netflix2.html"));
        ExportParser parser = new ExportParser();
        String contentParsed = parser.parse(new String(content,StandardCharsets.UTF_8));
        assertNotNull(contentParsed);
        System.out.println(contentParsed);
    }

    @Test
    void testExport3() throws IOException, ExportParserException{
        byte[] content = Files.readAllBytes(Paths.get("./src/test/resources/Jackson2.json"));
        ObjectMapper mapper = new ObjectMapper();
        Netflix contentObject = mapper.readValue(new String(content,StandardCharsets.UTF_8),new TypeReference<Netflix>() {});
        assertNotNull(contentObject);
    }
}
