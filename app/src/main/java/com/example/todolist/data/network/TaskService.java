package com.example.todolist.data.network;

import com.example.todolist.data.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaskService {
    @GET("todos")
    Call<List<Task>> getTasks(@Query("userId") int userId);
}
