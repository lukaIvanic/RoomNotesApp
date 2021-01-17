package com.example.roomnodoappjava.ui;

import android.content.Context;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomnodoappjava.MainActivity;
import com.example.roomnodoappjava.R;
import com.example.roomnodoappjava.model.NoDo;
import com.example.roomnodoappjava.model.NoDoViewModel;
import com.example.roomnodoappjava.util.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoDoAdapter extends RecyclerView.Adapter<NoDoAdapter.NoDoViewHolder>  {


    private final LayoutInflater noDoInflater;
    private List<NoDo> noDoList; //Cached copy of NoDos
    private ItemClickListener itemClickListener;
    private ArrayList<String> selectedList = new ArrayList<>();
//    private ItemTouchHelper touchHelper;
    private boolean isEnable = false;

    public NoDoAdapter(Context context, ItemClickListener itemClickListener){
        noDoInflater = LayoutInflater.from(context);
        this.itemClickListener = itemClickListener;
    }

    public void setTouchHelper(ItemTouchHelper itemTouchHelper){
//        this.touchHelper = itemTouchHelper;
    }

    public class NoDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView noDoTitleTV, noDoDescTV;
        GestureDetector gestureDetector;

        public NoDoViewHolder(@NonNull View itemView) {
            super(itemView);
            noDoTitleTV = itemView.findViewById(R.id.text_view_title);
            noDoDescTV = itemView.findViewById(R.id.text_view_desc);
            itemView.setOnClickListener(this);
        }

//        implemented with touch listener
        @Override
        public void onClick(View view) {
            if(isEnable){
                ClickItem(noDoTitleTV.getText().toString(), view);
            }else{
                itemClickListener.OnItemClick(getAdapterPosition(), noDoList.get(getAdapterPosition()));
            }
        }
    }

    private void ClickItem(String s, View view) {
        if(selectedList.contains(s)) {
            view.findViewById(R.id.card_view).setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.white));
            selectedList.remove(s);
            return;
        }
        view.findViewById(R.id.card_view).setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.teal_200));
        selectedList.add(s);
    }

    public interface ItemClickListener{
        void OnItemClick(int id, NoDo noDo);
    }

    @NonNull
    @Override
    public NoDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = noDoInflater.inflate(R.layout.rec_row, parent, false);
        return new NoDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoDoViewHolder holder, int position) {
            if (noDoList != null){
                NoDo current = noDoList.get(holder.getAdapterPosition());
                if(current != null)
                holder.noDoTitleTV.setText(current.getNoDoTitle());
                holder.noDoDescTV.setText(current.getNoDoDesc());
            }else{
                holder.noDoTitleTV.setText("No no todo!");
            }
    }

    public void setNoDos(List<NoDo> noDos){
        noDoList = noDos;
        notifyDataSetChanged();
    }

    public NoDo getNoDo(int id){
        return noDoList.get(id);
    }

    @Override
    public int getItemCount() {
        if(noDoList != null)
            return noDoList.size();
        return 0;
    }

    public List<NoDo> getNoDoList() {
        return noDoList;
    }
}
