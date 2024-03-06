package ru.cource.springTask.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.cource.springTask.write.model.Login;
import ru.cource.springTask.write.model.User;
import ru.cource.springTask.write.repository.LoginRepository;
import ru.cource.springTask.write.repository.UserRepository;
import ru.cource.springTask.data.LogData;
import ru.cource.springTask.log.LogTransformation;
import ru.cource.springTask.log.Loggerable;
import ru.cource.springTask.read.FolderReader;

import java.io.IOException;
import java.util.*;

@Component
public class H2Writer implements Writer {
    @Autowired
    private FolderReader folderReader;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private Loggerable logger;
    @LogTransformation
    public void write() {
        var usersSet = new HashSet<String>();
        userRepository.findAll().forEach(user -> usersSet.add(user.getUserName()));

        // users add
        List<User> usersList = new ArrayList<>();
        folderReader.getLogsData()
                .forEach(fileReader -> fileReader.getLogData()
                        .forEach(datable -> {
                            var logData = (LogData)datable;
                            if (!usersSet.contains(logData.getUserName())){
                                usersSet.add(logData.getUserName());
                                usersList.add(new User(0,
                                                        logData.getUserName(),
                                                        logData.getFio()));
                            }

                        }));

        userRepository.saveAll(usersList);
        var usersMap = new HashMap<String, User>();
        userRepository.findAll().forEach(user -> usersMap.put(user.getUserName(), user));

        // logs add
        List<Login> loginsList = new ArrayList<>();
        folderReader.getLogsData()
                .forEach(fileReader -> fileReader.getLogData()
                        .forEach(datable -> {
                            var logData = (LogData)datable;

                            if (logData.isError())
                            {
                                try {
                                    logger.logError(logData.toString());
                                } catch (IOException e) {
                                    throw new RuntimeException("Write error log info to file: " + e);
                                }
                            }
                            else {
                                loginsList.add(new Login(0,
                                        logData.getAccessDate(),
                                        usersMap.get(logData.getUserName()),
                                        logData.getApplication()));
                            }
                        }));
        loginRepository.saveAll(loginsList);
    }
}