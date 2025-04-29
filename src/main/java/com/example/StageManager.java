
package com.example;

import com.example.stage.CancelStageTask;
import com.example.stage.CompleteStageTask;
import com.example.stage.InitStageTask;
import com.example.stage.ProcessStageTask;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class StageManager implements AutoCloseable {
    private final ThreadPoolExecutor initExecutor;
    private final ThreadPoolExecutor processExecutor;
    private final ThreadPoolExecutor completeExecutor;
    private final ThreadPoolExecutor cancelExecutor;

    public StageManager() {
        // Custom ThreadFactory cho init stage
        ThreadFactory initThreadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "init-worker-" + threadNumber.getAndIncrement());
                t.setDaemon(false);
                return t;
            }
        };

        // Custom ThreadFactory cho process stage
        ThreadFactory processThreadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "process-worker-" + threadNumber.getAndIncrement());
                t.setDaemon(false);
                return t;
            }
        };

        // Custom ThreadFactory cho complete stage
        ThreadFactory completeThreadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "complete-worker-" + threadNumber.getAndIncrement());
                t.setDaemon(false);
                return t;
            }
        };

        // Custom ThreadFactory cho cancel stage
        ThreadFactory cancelThreadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "cancel-worker-" + threadNumber.getAndIncrement());
                t.setDaemon(false);
                return t;
            }
        };

        // ThreadPoolExecutor cấu hình tùy chỉnh cho init stage
        this.initExecutor = new ThreadPoolExecutor(
                1,                       // corePoolSize
                2,                       // maximumPoolSize
                60, TimeUnit.SECONDS,    // keepAliveTime
                new ArrayBlockingQueue<>(100), // workQueue
                initThreadFactory,       // threadFactory
                new ThreadPoolExecutor.CallerRunsPolicy() // rejectionHandler
        );

        // ThreadPoolExecutor cấu hình tùy chỉnh cho process stage
        this.processExecutor = new ThreadPoolExecutor(
                2,                       // corePoolSize
                3,                       // maximumPoolSize
                60, TimeUnit.SECONDS,    // keepAliveTime
                new ArrayBlockingQueue<>(200), // workQueue
                processThreadFactory,    // threadFactory
                new ThreadPoolExecutor.CallerRunsPolicy() // rejectionHandler
        );

        // ThreadPoolExecutor cấu hình tùy chỉnh cho complete stage
        this.completeExecutor = new ThreadPoolExecutor(
                1,                       // corePoolSize
                2,                       // maximumPoolSize
                60, TimeUnit.SECONDS,    // keepAliveTime
                new ArrayBlockingQueue<>(100), // workQueue
                completeThreadFactory,   // threadFactory
                new ThreadPoolExecutor.CallerRunsPolicy() // rejectionHandler
        );

        // ThreadPoolExecutor cấu hình tùy chỉnh cho cancel stage
        this.cancelExecutor = new ThreadPoolExecutor(
                1,                       // corePoolSize
                1,                       // maximumPoolSize
                60, TimeUnit.SECONDS,    // keepAliveTime
                new ArrayBlockingQueue<>(50), // workQueue
                cancelThreadFactory,     // threadFactory
                new ThreadPoolExecutor.CallerRunsPolicy() // rejectionHandler
        );

        // Thiết lập prestart threads
        this.initExecutor.prestartAllCoreThreads();
        this.processExecutor.prestartAllCoreThreads();
        this.completeExecutor.prestartAllCoreThreads();
        this.cancelExecutor.prestartAllCoreThreads();
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
        shutdownExecutor(initExecutor, "Init");
        shutdownExecutor(processExecutor, "Process");
        shutdownExecutor(completeExecutor, "Complete");
        shutdownExecutor(cancelExecutor, "Cancel");
    }

    private void shutdownExecutor(ThreadPoolExecutor executor, String name) {
        try {
            System.out.println("Shutting down " + name + " executor...");
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println(name + " executor did not terminate in the specified time.");
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println(name + " executor did not terminate.");
                }
            }
            System.out.println(name + " executor has been shutdown successfully.");
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Phương thức để lấy thông tin trạng thái thread pool
    public void printThreadPoolsInfo() {
        System.out.println("\n--- Thread Pools Status ---");
        printPoolInfo("Init", initExecutor);
        printPoolInfo("Process", processExecutor);
        printPoolInfo("Complete", completeExecutor);
        printPoolInfo("Cancel", cancelExecutor);
        System.out.println("-------------------------\n");
    }

    private void printPoolInfo(String name, ThreadPoolExecutor executor) {
        System.out.println(name + " Pool - " +
                "Active: " + executor.getActiveCount() + ", " +
                "Core: " + executor.getCorePoolSize() + ", " +
                "Max: " + executor.getMaximumPoolSize() + ", " +
                "Completed: " + executor.getCompletedTaskCount() + ", " +
                "Queue size: " + executor.getQueue().size());
    }
}