package com.example.todolist.data.model;

import java.util.Random;

public class Task {
    private int userId;
    public int id;
    public String title;
    public boolean completed;

    private Task() {
    }

    public static Task newInstance(int userId, String title) {
        Task task = new Task();
        task.userId = userId;
        task.id = new Random().nextInt();
        task.title = title;
        task.completed = false;
        return task;
    }
}
