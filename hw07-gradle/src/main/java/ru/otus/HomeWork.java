package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorUpperField10;
import ru.otus.processor.provider.SystemDateTimeProvider;

import java.util.List;

public class HomeWork {
    public static void main(String[] args) {
        var processors = List.of(
                new ProcessorConcatFields(),
                new LoggerProcessor(new ProcessorUpperField10())
        );

        var complexProcessor = new ComplexProcessor(processors, ex -> {});

        var listenerPrinter = new HistoryListener();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field11("field1")
                .field12("field2")
                .field13(new ObjectForMessage())
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}