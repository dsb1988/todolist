package com.example.todolist;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.todolist.data.ResultCallback;
import com.example.todolist.data.model.Task;
import com.example.todolist.data.repository.Repository;
import com.example.todolist.data.repository.TasksMockRepository;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskMockRepositoryTest {
    private static final int USERID = 1;

    private static final int MOCKED_ITEM_COUNT = 20;
    private static final int MOCKED_ITEM_USERID = 1;
    private static final int MOCKED_ITEM_ID = 1;
    private static final String MOCKED_ITEM_TITLE = "delectus aut autem";

    private static List<Task> MOCKED_TASKS;

    @BeforeClass
    public static void setUp() {
        Repository<Task> taskRepository =
                new TasksMockRepository(InstrumentationRegistry.getTargetContext());
        taskRepository.get(USERID, new ResultCallback<List<Task>>() {
            @Override
            public void onResult(List<Task> result) {
                MOCKED_TASKS = result;
            }
        });
    }

    @Test
    public void testMockedItemCount() {
        Assert.assertEquals(MOCKED_ITEM_COUNT, MOCKED_TASKS.size());
    }

    @Test
    public void testMockedItemUserId() {
        Task task = MOCKED_TASKS.get(0);
        Assert.assertEquals(MOCKED_ITEM_USERID, task.userId);
    }

    @Test
    public void testMockedItemId() {
        Task task = MOCKED_TASKS.get(0);
        Assert.assertEquals(MOCKED_ITEM_ID, task.id);
    }

    @Test
    public void testMockedItemTitle() {
        Task task = MOCKED_TASKS.get(0);
        Assert.assertEquals(MOCKED_ITEM_TITLE, task.title);
    }

    @Test
    public void testMockedItemCompleted() {
        Task task = MOCKED_TASKS.get(0);
        Assert.assertFalse(task.completed);
    }
}
