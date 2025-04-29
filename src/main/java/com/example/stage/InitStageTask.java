package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
public class InitStageTask<T> extends AbstractStageTask<T> {
    public InitStageTask(ThreadPoolTaskExecutor executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        log.info("Init Stage: {}, data: {}",  stage.getId(),stage.getData());
        stage.setStatus("Init");
        // Giả lập xử lý
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}