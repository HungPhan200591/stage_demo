package com.example;

import java.util.concurrent.ExecutorService;

public class CompleteStageTask extends AbstractStageTask {
    public CompleteStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage stage) {
        System.out.println(Thread.currentThread().getName() + " - Complete Stage: " + stage.getId());
        stage.setStatus("Completed");
        // Giả lập xử lý
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}