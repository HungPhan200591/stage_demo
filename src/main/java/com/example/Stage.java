package com.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stage<T> {
    private String id;
    private String status;
    private T data;

    public Stage(String id, T data) {
        this.id = id;
        this.data = data;
        this.status = "New";
    }
}