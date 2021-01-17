package com.example.roomnodoappjava.util;

import com.example.roomnodoappjava.model.NoDo;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);
    void onItemSwipe(int position);

}
