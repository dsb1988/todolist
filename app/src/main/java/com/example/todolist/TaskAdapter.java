package com.example.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.todolist.data.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context mContext;
    private final List<Task> mTasks;

    TaskAdapter(Context context, List<Task> tasks) {
        mContext = context;
        mTasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Task task = mTasks.get(position);
        holder.mRoot.setBackgroundColor(getBackgroundColor(task.completed));
        holder.mTitle.setText(task.title);
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxLines = holder.mTitle.getMaxLines();
                holder.mTitle.setSingleLine(maxLines != 1);
            }
        });
        holder.mCompleted.setOnCheckedChangeListener(null);
        holder.mCompleted.setChecked(task.completed);
        holder.mCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                task.completed = b;
                notifyDataSetChanged();
            }
        });
    }

    private int getBackgroundColor(boolean completed) {
        return mContext.getResources().getColor(
                completed ? R.color.colorTaskDone : R.color.colorTaskUndone);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mRoot;
        TextView mTitle;
        CheckBox mCompleted;

        ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView;
            mTitle = itemView.findViewById(R.id.title);
            mCompleted = itemView.findViewById(R.id.completed);
        }
    }
}
