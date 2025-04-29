package com.example;

import java.util.concurrent.ExecutorService;

public class InitStageTask extends AbstractStageTask {
    public InitStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage stage) {
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