package com.example;

import java.util.concurrent.ExecutorService;

public abstract class AbstractStageTask implements StageTask {
    protected final ExecutorService executorService;

    protected AbstractStageTask(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute(Stage stage) {
        executorService.execute(() -> {
            preProcess(stage);
            doProcess(stage);
            postProcess(stage);
        });
    }

    protected void preProcess(Stage stage) {
        System.out.println(Thread.currentThread().getName() + " - Chuẩn bị xử lý cho stage: " + stage.getId());
    }

    protected abstract void doProcess(Stage stage);

    protected void postProcess(Stage stage) {
        System.out.println(Thread.currentThread().getName() + " - Kết thúc xử lý cho stage: " + stage.getId() + 
            ". Trạng thái hiện tại: " + stage.getStatus());
    }
}