package synchro.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import synchro.inner.read.FilesRepeater;

@Component
public class ReadTask {

    @Autowired
    private FilesRepeater fileRepeater;
    
    @Scheduled(cron = "0/5 * *  * * ? ")
    public void task(){
        fileRepeater.initFiles();
    }
}
