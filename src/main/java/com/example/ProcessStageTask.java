package com.example;

import java.util.concurrent.ExecutorService;

public class ProcessStageTask extends AbstractStageTask {
    public ProcessStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage stage) {
        System.out.println(Thread.currentThread().getName() + " - Process Stage: " + stage.getId());
        stage.setStatus("Processing");
        // Giả lập xử lý
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}