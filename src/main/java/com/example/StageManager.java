package com.example;

import com.example.stage.CancelStageTask;
import com.example.stage.CompleteStageTask;
import com.example.stage.InitStageTask;
import com.example.stage.ProcessStageTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.UUID;

public class StageManager implements AutoCloseable {
    private final ExecutorService initExecutor;
    private final ExecutorService processExecutor;
    private final ExecutorService completeExecutor;
    private final ExecutorService cancelExecutor;

    public StageManager() {
        this.initExecutor = Executors.newFixedThreadPool(2);
        this.processExecutor = Executors.newFixedThreadPool(3);
        this.completeExecutor = Executors.newFixedThreadPool(2);
        this.cancelExecutor = Executors.newSingleThreadExecutor();
    }

    public <T> void startWorkflow(T data) {
        String stageId = UUID.randomUUID().toString().substring(0, 8);
        Stage<T> stage = new Stage<>(stageId, data);

        try {
            InitStageTask<T> initStageTask = new InitStageTask<>(initExecutor);
            initStageTask.execute(stage);

            ProcessStageTask<T> processStageTask = new ProcessStageTask<>(processExecutor);
            processStageTask.execute(stage);

            CompleteStageTask<T> completeStageTask = new CompleteStageTask<>(completeExecutor);
            completeStageTask.execute(stage);

        } catch (Exception e) {
            CancelStageTask<T> cancelStageTask = new CancelStageTask<>(cancelExecutor);
            cancelStageTask.execute(stage);
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