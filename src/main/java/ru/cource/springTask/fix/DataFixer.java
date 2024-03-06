package ru.cource.springTask.fix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.cource.springTask.data.LogData;
import ru.cource.springTask.read.FolderReader;

import java.util.List;
import java.util.function.Consumer;

@Component
public final class DataFixer implements DataFixable{
    @Autowired
    List<Consumer<LogData>> dataFixer;
    @Autowired
    FolderReader folderReader;
    public void fix() {
       folderReader.getLogsData()
               .forEach(fileReader -> fileReader.getLogData()
                       .forEach(datable -> {
                           var logData = (LogData)datable;
                           dataFixer.forEach(consumer -> consumer.accept(logData));
            }));
    }
}
