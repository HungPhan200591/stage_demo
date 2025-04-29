package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Slf4j
public class ProcessStageTask<T> extends AbstractStageTask<T> {

    public ProcessStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        log.info("{} - Process Stage: {}, data: {}", Thread.currentThread().getName(), stage.getId(), stage.getData());
        stage.setStatus("Processing");
        // Giả lập xử lý
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}