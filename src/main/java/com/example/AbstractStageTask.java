package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Slf4j
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
        log.info("{} - Chuẩn bị xử lý cho stage: {}, data: {}", Thread.currentThread().getName(), stage.getId(), stage.getData());
    }

    protected abstract void doProcess(Stage<T> stage);

    protected void postProcess(Stage<T> stage) {
        log.info("{} - Kết thúc xử lý cho stage: {}, data: {}, trạng thái hiện tại: {}", Thread.currentThread().getName(), stage.getId(), stage.getData(), stage.getStatus());
    }
}