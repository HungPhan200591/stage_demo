package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StageManager implements AutoCloseable {
    private final ExecutorService initExecutor;
    private final ExecutorService processExecutor;
    private final ExecutorService completeExecutor;
    private final ExecutorService cancelExecutor;
    private Stage currentStage;

    public StageManager() {
        this.initExecutor = Executors.newFixedThreadPool(2);
        this.processExecutor = Executors.newFixedThreadPool(3);
        this.completeExecutor = Executors.newFixedThreadPool(2);
        this.cancelExecutor = Executors.newSingleThreadExecutor();
    }

    public void startWorkflow() {
        currentStage = new Stage("1");

        try {
            StageTask initStageTask = new InitStageTask(initExecutor);
            initStageTask.execute(currentStage);

            StageTask processStageTask = new ProcessStageTask(processExecutor);
            processStageTask.execute(currentStage);

            StageTask completeStageTask = new CompleteStageTask(completeExecutor);
            completeStageTask.execute(currentStage);

        } catch (Exception e) {
            new CancelStageTask(cancelExecutor).execute(currentStage);
        }
    }

    @Override
    public void close() {
        initExecutor.shutdown();
        processExecutor.shutdown();
        completeExecutor.shutdown();
        cancelExecutor.shutdown();
    }
}