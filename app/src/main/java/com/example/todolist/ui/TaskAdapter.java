package com.example.todolist.ui;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.data.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private static final float ALPHA_TASK_DONE = 0.50f;
    private static final float ALPHA_TASK_UNDONE = 1.0f;
    private Context mContext;
    private final List<Task> mTasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        mContext = context;
        mTasks = tasks;
        setHasStableIds(true);
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

        holder.mRoot.setAlpha(task.completed ? ALPHA_TASK_DONE : ALPHA_TASK_UNDONE);
        holder.mRoot.setBackgroundColor(getBackgroundColor(task.completed));

        holder.mTitle.setText(task.title);
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxLines = holder.mTitle.getMaxLines();
                holder.mTitle.setSingleLine(maxLines != 1);
            }
        });
        holder.mTitle.setPaintFlags(task.completed ?
                addStrikeThrough(holder.mTitle) : removeStrikeThrough(holder.mTitle));

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

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    @Override
    public long getItemId(int position) {
        return mTasks.get(position).id;
    }

    private int getBackgroundColor(boolean completed) {
        return mContext.getResources().getColor(
                completed ? R.color.colorTaskDone : R.color.colorTaskUndone);
    }

    private int addStrikeThrough(TextView textView) {
        return textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG;
    }

    private int removeStrikeThrough(TextView textView) {
        return textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG;
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
