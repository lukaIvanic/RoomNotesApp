package com.example.roomnodoappjava.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomnodoappjava.R;

public class NodoTouchHelper extends androidx.recyclerview.widget.ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter adapter;

    public NodoTouchHelper(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.teal_200));
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG){
            //viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.white));
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = androidx.recyclerview.widget.ItemTouchHelper.UP | androidx.recyclerview.widget.ItemTouchHelper.DOWN;
        final int swipeFlags = androidx.recyclerview.widget.ItemTouchHelper.START | androidx.recyclerview.widget.ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true; //Must be called true otherwise onMove won't work
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemSwipe(viewHolder.getAdapterPosition());
    }
}
