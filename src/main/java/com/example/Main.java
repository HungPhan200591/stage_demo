package com.example;

public class Main {
    public static void main(String[] args) {
        try (StageManager stageManager = new StageManager()) {
            stageManager.startWorkflow();
            // Thêm một khoảng thời gian chờ để đảm bảo các task hoàn thành
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}