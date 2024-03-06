package ru.cource.springTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cource.springTask.data.*;
import ru.cource.springTask.fix.DataFixer;
import ru.cource.springTask.read.FolderReader;
import static ru.cource.springTask.Utils.*;

@SpringBootTest(classes = {FolderReader.class, DataFixer.class, FixFIO.class, FixData.class, FixApplicationType.class, AppConfig.class})
public class DataFixerTest {
    @Autowired
    FolderReader dataReader;
    @Autowired
    DataFixer dataFixer;
    @Mock
    FolderReader dataReaderFixedMock;
    @BeforeEach
    public void setUpAllFixedReaderData(){
        FolderReader folderReader = getAllFixedReadersData();

        Mockito.when(dataReaderFixedMock.get())
                .thenReturn(folderReader);
    }
    @Test
    public void fixFileCheck(){
        dataReader.get();
        dataFixer.fix();
        Assertions.assertEquals(dataReader, dataReaderFixedMock.get());
    }
}
