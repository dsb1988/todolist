package com.example.todolist.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class SwipeController extends ItemTouchHelper.Callback {
    public interface RemoveCallback {
        void onRemove(int position);
    }

    private RemoveCallback mRemoveCallback;

    public SwipeController(RemoveCallback callback) {
        mRemoveCallback = callback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT && mRemoveCallback != null) {
            mRemoveCallback.onRemove(viewHolder.getAdapterPosition());
        }
    }
}
