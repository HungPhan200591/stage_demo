package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Slf4j
public class CancelStageTask<T> extends AbstractStageTask<T> {
    public CancelStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        log.info("{} - Cancel Stage: {}", Thread.currentThread().getName(), stage.getId());
        stage.setStatus("Cancelled");
    }
}