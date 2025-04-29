package com.example;

import com.example.dto.Person;

public class Main {
    public static void main(String[] args) {
        try (StageManager stageManager = new StageManager()) {
            // Xử lý đối tượng String
            stageManager.startWorkflow("Đối tượng 1");

            // Xử lý đối tượng Integer
            stageManager.startWorkflow(100);

            // Xử lý đối tượng tùy chỉnh
            Person person = new Person("John Doe", 30);
            stageManager.startWorkflow(person);

            // Thêm một khoảng thời gian chờ để đảm bảo các task hoàn thành
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}