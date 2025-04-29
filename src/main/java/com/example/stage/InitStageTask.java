package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;

import java.util.concurrent.ExecutorService;

public class InitStageTask<T> extends AbstractStageTask<T> {
    public InitStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        System.out.println(Thread.currentThread().getName() + " - Init Stage: " + stage.getId());
        stage.setStatus("Init");
        // Giả lập xử lý
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}