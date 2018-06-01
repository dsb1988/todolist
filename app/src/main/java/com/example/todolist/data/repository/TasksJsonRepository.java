package com.example.todolist.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.todolist.data.ResultCallback;
import com.example.todolist.data.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TasksJsonRepository implements Repository<Task> {

    private static final String TAG = TasksJsonRepository.class.getSimpleName();
    public static final String FILE_NAME = "tasks.json";
    private final Context mContext;

    public TasksJsonRepository(Context context) {
        mContext = context;
    }

    @Override
    public void get(int userId, ResultCallback<List<Task>> callback) {
        Gson gson = new Gson();
        List<Task> tasks = gson.fromJson(getTasksJson(), new TypeToken<ArrayList<Task>>(){}.getType());
        callback.onResult(tasks);
    }

    private String getTasksJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = mContext.getAssets().open(FILE_NAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String s;
            while ((s = in.readLine()) != null) {
                stringBuilder.append(s);
            }
            in.close();
        } catch (IOException e) {
            Log.e(TAG, "getTasksJson error: ", e);
        }
        return stringBuilder.toString();
    }
}
