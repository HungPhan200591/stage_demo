package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Slf4j
public class CompleteStageTask<T> extends AbstractStageTask<T> {

    public CompleteStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        log.info("{} - Complete Stage: {}, data: {}", Thread.currentThread().getName(), stage.getId(), stage.getData());
        stage.setStatus("Completed");
        // Giả lập xử lý
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}