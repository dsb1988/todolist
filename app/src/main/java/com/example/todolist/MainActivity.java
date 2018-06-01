package com.example.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.todolist.data.ResultCallback;
import com.example.todolist.data.model.Task;
import com.example.todolist.data.repository.Repository;
import com.example.todolist.data.repository.TasksNetworkRepository;
import com.example.todolist.ui.SwipeController;
import com.example.todolist.ui.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int USERID = 1;
    private static final String BUNDLE_TASK_LIST = "bundle_task_list";

    private EditText mTaskName;
    private ProgressBar mProgress;
    private TaskAdapter mTaskAdapter;
    private ArrayList<Task> mTaskList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskName = findViewById(R.id.taskName);

        mRecyclerView = findViewById(R.id.taskList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mTaskList = new ArrayList<>();
        mTaskAdapter = new TaskAdapter(this, mTaskList);
        mRecyclerView.setAdapter(mTaskAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeController(new SwipeController.RemoveCallback() {
            @Override
            public void onRemove(int position) {
                mTaskList.remove(position);
                mTaskAdapter.notifyDataSetChanged();
            }
        }));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mProgress = findViewById(R.id.progress);

        if (savedInstanceState == null) {
            loadTasks(USERID);
        } else {
            restoreTasks(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_TASK_LIST, mTaskList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        restoreTasks(savedInstanceState);
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
        showProgress(true);
        showList(false);
        Repository<Task> taskRepository = new TasksNetworkRepository();
        taskRepository.get(userId, new ResultCallback<List<Task>>() {
            @Override
            public void onResult(List<Task> result) {
                showProgress(false);
                showList(true);
                mTaskList.addAll(result);
                mTaskAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showList(boolean show) {
        mRecyclerView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showProgress(boolean show) {
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void restoreTasks(Bundle savedInstanceState) {
        List<Task> savedTasks = savedInstanceState.getParcelableArrayList(BUNDLE_TASK_LIST);
        if (savedTasks != null) {
            mTaskList.clear();
            mTaskList.addAll(savedTasks);
        } else {
            loadTasks(USERID);
        }
    }
}
