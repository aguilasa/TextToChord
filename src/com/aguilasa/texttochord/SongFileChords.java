package com.aguilasa.texttochord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongFileChords {

    private UUID id;
    private String name;
    private String artist;
    private String tone;
    private String original;
    private String minor;
    private String chords;

    public String toJson(int spaces) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter()
                .withDefaultPrettyPrinter()
                .with(new com.fasterxml.jackson.core.util.DefaultPrettyPrinter()
                        .withArrayIndenter(new com.fasterxml.jackson.core.util.DefaultIndenter().withIndent(" ".repeat(spaces))));
        try {
            return writer.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }

    public void saveToFile(String filePath, int spaces) {
        String json = toJson(spaces);
        try {
            Files.write(Paths.get(filePath), json.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Error saving JSON to file", e);
        }
    }
}
