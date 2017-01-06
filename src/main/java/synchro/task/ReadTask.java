package synchro.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.inner.read.InnerFilesRepeater;

@Component
public class ReadTask {

    @Autowired
    private InnerFilesRepeater fileRepeater;
    
    public void task(){
        fileRepeater.initFiles();
    }
}
