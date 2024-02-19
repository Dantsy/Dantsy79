package chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();
        CsvMapper csvMapper = new CsvMapper();
        YAMLMapper yamlMapper = new YAMLMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        try {
            String json = new String(Files.readAllBytes(Paths.get("hw08-gradle\\src\\main\\java\\Chat\\sms.json")));
            Root root = objectMapper.readValue(json, Root.class);
            Map<String, List<ChatSession>> chatSessionsByNumber = root.getChatSessions().stream()
                    .collect(Collectors.groupingBy(chatSession -> chatSession.getMessages().get(0).getBelongNumber(),
                            Collectors.toList()));
            for (List<ChatSession> chatSessions : chatSessionsByNumber.values()) {
                for (ChatSession chatSession : chatSessions) {
                    chatSession.getMessages().sort(Comparator.comparingLong(chat.Message::getDate));
                }
            }

            System.out.println("Starting to print chat sessions...");
            chatSessionsByNumber.forEach((number, sessions) -> {
                System.out.println("Belong Number: " + number);
                sessions.forEach(session -> {
                    System.out.println("Chat ID: " + session.getChatId());
                    System.out.println("Chat Identifier: " + session.getChatIdentifier());
                    session.getMembers().forEach(member -> {
                        System.out.println("Member First Name: " + member.getFirst());
                        System.out.println("Member Phone Number: " + member.getPhoneNumber());
                    });
                    session.getMessages().forEach(message -> {
                        System.out.println("Message Text: " + message.getText());
                        System.out.println("Message Date: " + dateFormat.format(message.getDate()));
                    });
                });
            });
            System.out.println("Finished printing chat sessions.");
            System.out.println("Finished printing chat sessions.");
            // Сериализация в JSON
            objectMapper.writeValue(new File("output.json"), root);
            System.out.println("Данные успешно сериализованы в JSON в файл output.json");
            // Сериализация в XML
            xmlMapper.writeValue(new File("output.xml"), root);
            System.out.println("Данные успешно сериализованы в XML в файл output.xml");
            // Сериализация в CSV
            List<MessageCSV> messageCSVList = new ArrayList<>();
            for (List<ChatSession> chatSessions : chatSessionsByNumber.values()) {
                for (ChatSession chatSession : chatSessions) {
                    for (chat.Message message : chatSession.getMessages()) {
                        messageCSVList.add(new MessageCSV(
                                message.getBelongNumber(),
                                dateFormat.format(message.getDate()),
                                message.getText()
                        ));
                    }
                }
            }
            CsvSchema csvSchema = csvMapper.schemaFor(MessageCSV.class).withHeader();
            csvMapper.writer(csvSchema).writeValue(new File("output.csv"), messageCSVList);
            System.out.println("Данные успешно сериализованы в CSV в файл output.csv");
            yamlMapper.writeValue(new File("output.yaml"), root);
            System.out.println("Данные успешно сериализованы в YAML в файл output.yaml");
            try (FileOutputStream fileOut = new FileOutputStream("output.ser");
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(root);
                System.out.println("Данные успешно сериализованы в Java Serialization в файл output.ser");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
