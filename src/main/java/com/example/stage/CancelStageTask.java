package com.example.stage;

import com.example.AbstractStageTask;
import com.example.Stage;

import java.util.concurrent.ExecutorService;

public class CancelStageTask<T> extends AbstractStageTask<T> {
    public CancelStageTask(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    protected void doProcess(Stage<T> stage) {
        System.out.println(Thread.currentThread().getName() + " - Cancel Stage: " + stage.getId());
        stage.setStatus("Cancelled");
    }
}