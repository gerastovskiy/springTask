package ru.cource.springTask;

import ru.cource.springTask.data.Datable;
import ru.cource.springTask.data.LogData;
import ru.cource.springTask.read.FileReader;
import ru.cource.springTask.read.FolderReader;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Utils {
    public static final String filePath = "src/test/resources/logs/";
    public static LogData getCorrectLogData()
    {
        LogData correctLog = new LogData();
        correctLog.setUserName("testCorrect");
        correctLog.setFio("Correct Correctiovich");
        correctLog.setAccessDate(Timestamp.valueOf(LocalDateTime.of(2020,01,01,01,02,03)));
        correctLog.setApplication("web");

        return correctLog;
    }
    public static LogData getIncorrectLogData()
    {
        LogData incorrectLog = new LogData();
        incorrectLog.setUserName("testIncorrect");
        incorrectLog.setFio("incorrect incorrectovich");
        incorrectLog.setApplication("app");

        return incorrectLog;
    }
    public static LogData getFixedIncorrectLogData()
    {
        LogData incorrectLog = new LogData();
        incorrectLog.setUserName("testIncorrect");
        incorrectLog.setFio("Incorrect Incorrectovich");
        incorrectLog.setApplication("other: app");
        incorrectLog.setError(true);

        return incorrectLog;
    }
    public static FolderReader getAllReadersData()
    {
        List<Datable> logDataList = List.of(getCorrectLogData(), getIncorrectLogData());
        FileReader fileReader = new FileReader(logDataList, Path.of(filePath,"test.log"));

        FolderReader folderReader = new FolderReader(filePath, LogData.class);
        folderReader.addLogsData(fileReader);

        return folderReader;
    }
    public static FolderReader getAllFixedReadersData()
    {
        List<Datable> logDataList = List.of(getCorrectLogData(), getFixedIncorrectLogData());
        FileReader fileReader = new FileReader(logDataList, Path.of(filePath,"test.log"));

        FolderReader folderReader = new FolderReader(filePath, LogData.class);
        folderReader.addLogsData(fileReader);

        return folderReader;
    }
    public static FolderReader getAllCorrectReadersData()
    {
        List<Datable> logDataList = List.of(getCorrectLogData());
        FileReader fileReader = new FileReader(logDataList, Path.of(filePath,"test.log"));

        FolderReader folderReader = new FolderReader(filePath, LogData.class);
        folderReader.addLogsData(fileReader);

        return folderReader;
    }
}
