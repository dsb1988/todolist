package com.example.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.data.ResultCallback;
import com.example.todolist.data.repository.Repository;
import com.example.todolist.data.model.Task;
import com.example.todolist.data.repository.TasksNetworkRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int USERID = 1;

    private EditText mTaskName;
    private TaskAdapter mTaskAdapter;
    private List<Task> mTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskName = findViewById(R.id.taskName);

        RecyclerView recyclerView = findViewById(R.id.taskList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mTaskList = new ArrayList<>();
        mTaskAdapter = new TaskAdapter(this, mTaskList);
        recyclerView.setAdapter(mTaskAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeController(new SwipeController.RemoveCallback() {
            @Override
            public void onRemove(int position) {
                mTaskList.remove(position);
                mTaskAdapter.notifyDataSetChanged();
            }
        }));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        loadTasks(USERID);
    }

    public void addTask(View view) {
        String taskName = mTaskName.getText().toString();
        if (!TextUtils.isEmpty(taskName)) {
            mTaskList.add(0, Task.newInstance(USERID, taskName));
            mTaskAdapter.notifyDataSetChanged();
            mTaskName.getText().clear();
        } else {
            Toast.makeText(this, "Task name cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTasks(int userId) {
        Repository<Task> taskRepository = new TasksNetworkRepository();
        taskRepository.get(userId, new ResultCallback<List<Task>>() {
            @Override
            public void onResult(List<Task> result) {
                mTaskList.addAll(result);
                mTaskAdapter.notifyDataSetChanged();
            }
        });
    }
}
