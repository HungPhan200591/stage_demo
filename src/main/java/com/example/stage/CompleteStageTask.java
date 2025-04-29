package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;

import java.util.concurrent.ExecutorService;

public class CompleteStageTask<T> extends AbstractStageTask<T> {

    public CompleteStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        System.out.println(Thread.currentThread().getName() + " - Complete Stage: " + stage.getId() + ", data: " + stage.getData());
        stage.setStatus("Completed");
        // Giả lập xử lý
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}