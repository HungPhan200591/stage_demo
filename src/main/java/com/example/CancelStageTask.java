package com.example;

import java.util.concurrent.ExecutorService;

public class CancelStageTask extends AbstractStageTask {
    public CancelStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage stage) {
        System.out.println(Thread.currentThread().getName() + " - Cancel Stage: " + stage.getId());
        stage.setStatus("Cancelled");
    }
}