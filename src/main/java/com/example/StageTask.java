package com.example;

public interface StageTask<T> {
    void execute(Stage<T> stage);
}