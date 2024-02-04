package Chat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        // Deserialization from JSON
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("Chat/sms.json");
        String jsonString = FileUtils.readFileToString(file, "UTF-8");
        Map<String, Object> jsonMap = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        List<ChatSession> chatSessions = objectMapper.convertValue(jsonMap.get("chat_sessions"), new TypeReference<List<ChatSession>>() {});

        // Create a new structure
        List<List<String>> newStructure = new ArrayList<>();
        for (ChatSession chatSession : chatSessions) {
            for (Message message : chatSession.getMessages()) {
                List<String> row = new ArrayList<>();
                row.add(chatSession.getChatIdentifier());
                row.add(chatSession.getMembers().get(0).getLast());
                row.add(message.getBelongNumber());
                row.add(message.getSendDate());
                row.add(message.getText());
                newStructure.add(row);
            }
        }

        // Grouping and sorting
        newStructure = newStructure.stream()
                .sorted(Comparator.comparing(o -> o.get(2)))
                .collect(Collectors.toList());

        // Serialization and writing to files in different formats
        // JSON
        objectMapper.writeValue(new File("output.json"), newStructure);

        // XML
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File("output.xml"), newStructure);

        // CSV
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(String[].class).withHeader();
        csvMapper.writer(schema).writeValue(new File("output.csv"), newStructure);

        // YAML
        YAMLMapper yamlMapper = new YAMLMapper();
        yamlMapper.writeValue(new File("output.yaml"), newStructure);

        // Deserialization and output to console from files
        // JSON
        List<List<String>> deserializedJson = objectMapper.readValue(new File("output.json"), new TypeReference<List<List<String>>>() {});
        System.out.println("Deserialized from JSON:");
        deserializedJson.forEach(System.out::println);

        // XML
        List<List<String>> deserializedXml = xmlMapper.readValue(new File("output.xml"), new TypeReference<List<List<String>>>() {});
        System.out.println("Deserialized from XML:");
        deserializedXml.forEach(System.out::println);

        // CSV
        List<Object> deserializedCsv = csvMapper.readerFor(List.class)
                .with(schema)
                .readValues(new File("output.csv"))
                .readAll();
        System.out.println("Deserialized from CSV:");
        deserializedCsv.forEach(System.out::println);

        // YAML
        List<List<String>> deserializedYaml = yamlMapper.readValue(new File("output.yaml"), new TypeReference<List<List<String>>>() {});
        System.out.println("Deserialized from YAML:");
        deserializedYaml.forEach(System.out::println);
    }
}