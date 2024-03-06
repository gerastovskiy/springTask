package ru.cource.springTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.cource.springTask.data.LogData;
import ru.cource.springTask.read.FolderReader;
import ru.cource.springTask.write.model.Login;
import ru.cource.springTask.write.model.User;
import ru.cource.springTask.write.repository.*;
import java.util.ArrayList;
import java.util.List;
import static ru.cource.springTask.Utils.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataWriterTest {
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    UserRepository userRepository;
    @Mock
    FolderReader dataReaderMock;
    @BeforeEach
    public void setUpAllCorrect(){
        FolderReader folderReader = getAllCorrectReadersData();

        Mockito.when(dataReaderMock.get())
                .thenReturn(folderReader);
    }
    public User getCorrectUser(){
        var logData = getCorrectLogData();
        return new User(0,
                            logData.getUserName(),
                            logData.getFio()
        );
    }
    public Login getCorrectLogin(User user){
        var logData = getCorrectLogData();
        return new Login(0,
                            logData.getAccessDate(),
                            user,
                            logData.getApplication()
        );
    }
    @Test
    public void writeFileCheck(){
        FolderReader dataReaderMockGet = dataReaderMock.get();

        List<User> usersList = new ArrayList<>();
        dataReaderMockGet.getLogsData()
            .forEach(fileReader -> fileReader.getLogData()
                    .forEach(datable -> {
                        var logData = (LogData)datable;
                        usersList.add(new User(0,
                                logData.getUserName(),
                                logData.getFio()));
                    }));

        userRepository.saveAll(usersList);
        var correctUser = getCorrectUser();
        var userFromH2 = userRepository.findByUserName(correctUser.getUserName());
        Assertions.assertEquals(correctUser.getFio(), userFromH2.getFio());

        List<Login> loginsList = new ArrayList<>();
        dataReaderMockGet.getLogsData()
                .forEach(fileReader -> fileReader.getLogData()
                        .forEach(datable -> {
                            var logData = (LogData)datable;
                            loginsList.add(new Login(0,
                                    logData.getAccessDate(),
                                    userFromH2,
                                    logData.getApplication()));
                        }));

        loginRepository.saveAll(loginsList);
        var correctLogin = getCorrectLogin(correctUser);
        var userLoginFromH2 = loginRepository.findByUser(userFromH2);
        Assertions.assertEquals(correctLogin.getAccessDate(), userLoginFromH2.getAccessDate());
        Assertions.assertEquals(correctLogin.getApplication(), userLoginFromH2.getApplication());
    }
}
