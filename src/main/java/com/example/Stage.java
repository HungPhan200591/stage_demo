package com.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stage {
    private String id;
    private String status;

    public Stage(String id) {
        this.id = id;
        this.status = "New";
    }
}