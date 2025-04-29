package com.example;

import com.example.stage.CancelStageTask;
import com.example.stage.CompleteStageTask;
import com.example.stage.InitStageTask;
import com.example.stage.ProcessStageTask;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.UUID;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class StageManager {

    private final ThreadPoolTaskExecutor initExecutor;
    private final ThreadPoolTaskExecutor processExecutor;
    private final ThreadPoolTaskExecutor completeExecutor;
    private final ThreadPoolTaskExecutor cancelExecutor;

    public <T> void startWorkflow(T data) {
        String stageId = UUID.randomUUID().toString().substring(0, 8);
        Stage<T> stage = new Stage<>(stageId, data);

        try {
            new InitStageTask<T>(initExecutor).execute(stage);
            new ProcessStageTask<T>(processExecutor).execute(stage);
            new CompleteStageTask<T>(completeExecutor).execute(stage);
        } catch (Exception e) {
            new CancelStageTask<T>(cancelExecutor).execute(stage);
        }
    }

    @PreDestroy
    public void shutdownExecutors() {
        shutdownExecutor(initExecutor, "Init");
        shutdownExecutor(processExecutor, "Process");
        shutdownExecutor(completeExecutor, "Complete");
        shutdownExecutor(cancelExecutor, "Cancel");
    }

    private void shutdownExecutor(ThreadPoolTaskExecutor executor, String name) {
        System.out.println("Shutting down " + name + " executor...");
        executor.shutdown();
    }

    public void printThreadPoolsInfo() {
        System.out.println("\n--- Thread Pools Status ---");
        printPoolInfo("Init", initExecutor);
        printPoolInfo("Process", processExecutor);
        printPoolInfo("Complete", completeExecutor);
        printPoolInfo("Cancel", cancelExecutor);
        System.out.println("---------------------------\n");
    }

    private void printPoolInfo(String name, ThreadPoolTaskExecutor executor) {
        System.out.println(name + " Pool - " +
                "Active: " + executor.getActiveCount() + ", " +
                "Core: " + executor.getCorePoolSize() + ", " +
                "Max: " + executor.getMaxPoolSize() + ", " +
                "Completed: " + executor.getThreadPoolExecutor().getCompletedTaskCount() + ", " +
                "Queue size: " + executor.getThreadPoolExecutor().getQueue().size());
    }
}
