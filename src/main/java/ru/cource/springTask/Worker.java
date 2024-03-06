package ru.cource.springTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.cource.springTask.fix.DataFixable;
import ru.cource.springTask.read.Reader;
import ru.cource.springTask.write.Writer;

@Component
public class Worker {
    @Autowired
    Reader dataReader;
    @Autowired
    DataFixable dataFixer;
    @Autowired
    Writer dataWriter;

    public void make(){
        dataReader.get();
        dataFixer.fix();
        dataWriter.write();
    }
}
