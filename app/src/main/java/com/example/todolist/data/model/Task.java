package com.example.todolist.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class Task implements Parcelable {
    private int userId;
    public int id;
    public String title;
    public boolean completed;

    private Task() {
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public static Task newInstance(int userId, String title) {
        Task task = new Task();
        task.userId = userId;
        task.id = new Random().nextInt();
        task.title = title;
        task.completed = false;
        return task;
    }

    private Task(Parcel in) {
        this.userId = in.readInt();
        this.id = in.readInt();
        this.title = in.readString();
        this.completed = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeByte((byte) (completed ? 1 : 0));
    }
}
