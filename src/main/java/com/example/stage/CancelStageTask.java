package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
public class CancelStageTask<T> extends AbstractStageTask<T> {
    public CancelStageTask(ThreadPoolTaskExecutor executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        log.info("Cancel Stage: {}, data: {}",  stage.getId(),stage.getData());
        stage.setStatus("Cancelled");
    }
}