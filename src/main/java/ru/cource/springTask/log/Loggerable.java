package ru.cource.springTask.log;

import java.io.IOException;

public interface Loggerable {
    void logError(String errorInfo) throws IOException;
    void logData(String logData) throws IOException;
}
