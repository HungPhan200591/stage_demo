package com.example;

import com.example.dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {



    public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = SpringApplication.run(Main.class, args);

        // Lấy StageManager từ context
        StageManager stageManager = context.getBean(StageManager.class);

        stageManager.startWorkflow("Object String");
        Thread.sleep(5000);

    }

}