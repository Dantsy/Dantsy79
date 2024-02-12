package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalTime;

public class ProcessorEvenSecond implements Processor {

    @Override
    public Message process(Message message) {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.getSecond() % 2 == 0) {
            throw new RuntimeException("Even second detected, throwing exception.");
        }
        return message;
    }
}