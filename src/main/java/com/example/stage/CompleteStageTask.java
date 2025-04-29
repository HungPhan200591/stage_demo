package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
public class CompleteStageTask<T> extends AbstractStageTask<T> {

    public CompleteStageTask(ThreadPoolTaskExecutor executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        log.info("Complete Stage: {}, data: {}",  stage.getId(),stage.getData());
        stage.setStatus("Completed");
        // Giả lập xử lý
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}