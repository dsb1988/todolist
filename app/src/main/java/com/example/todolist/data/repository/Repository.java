package com.example.todolist.data.repository;

import com.example.todolist.data.ResultCallback;
import com.example.todolist.data.model.Task;

import java.util.List;

public interface Repository<T> {
    void get(int userId, ResultCallback<List<Task>> callback);
}
