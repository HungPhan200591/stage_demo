package com.example;

import java.util.concurrent.ExecutorService;

public abstract class AbstractStageTask<T> implements StageTask<T> {
    protected final ExecutorService executorService;

    protected AbstractStageTask(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute(Stage<T> stage) {
        executorService.execute(() -> {
            preProcess(stage);
            doProcess(stage);
            postProcess(stage);
        });
    }

    protected void preProcess(Stage<T> stage) {
        System.out.println(Thread.currentThread().getName() + " - Chuẩn bị xử lý cho stage: " + stage.getId() + ", data: " + stage.getData());
    }

    protected abstract void doProcess(Stage<T> stage);

    protected void postProcess(Stage<T> stage) {
        System.out.println(Thread.currentThread().getName() + " - Kết thúc xử lý cho stage: " + stage.getId() + 
            ", data: " + stage.getData() + ", trạng thái hiện tại: " + stage.getStatus());
    }
}