package com.example.todolist.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.todolist.data.ResultCallback;
import com.example.todolist.data.model.Task;
import com.example.todolist.data.network.TaskService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TasksNetworkRepository implements Repository<Task> {
    private static final String TAG = TasksNetworkRepository.class.getSimpleName();
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    private TaskService mTaskService;

    public TasksNetworkRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTaskService = retrofit.create(TaskService.class);
    }

    @Override
    public void get(int userId, final ResultCallback<List<Task>> callback) {
        Call<List<Task>> call = mTaskService.getTasks(userId);
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {
                Log.d(TAG, "getTasksFailure: " + t.getMessage());
            }
        });
    }
}
