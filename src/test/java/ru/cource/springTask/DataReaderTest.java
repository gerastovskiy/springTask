package ru.cource.springTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cource.springTask.read.FolderReader;
import static ru.cource.springTask.Utils.*;

@SpringBootTest(classes = {FolderReader.class, AppConfig.class})
public class DataReaderTest {
    @Autowired
    FolderReader dataReader;
    @Mock
    FolderReader dataReaderMock;
    @BeforeEach
    public void setUpAllReaderData(){
        FolderReader folderReader = getAllReadersData();

        Mockito.when(dataReaderMock.get())
                .thenReturn(folderReader);
    }
    @Test
    public void readFileCheck(){
        Assertions.assertEquals(dataReaderMock.get(), dataReader.get());
    }
}
