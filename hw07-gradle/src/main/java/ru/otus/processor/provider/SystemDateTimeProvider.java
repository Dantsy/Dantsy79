package ru.otus.processor.provider;

import java.time.LocalDateTime;

public class SystemDateTimeProvider implements DateTimeProvider {
    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }

    @Override
    public int getCurrentSecond() {
        return LocalDateTime.now().getSecond();
    }
}