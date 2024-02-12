package ru.otus.processor.provider;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime getDate();
    int getCurrentSecond();
}