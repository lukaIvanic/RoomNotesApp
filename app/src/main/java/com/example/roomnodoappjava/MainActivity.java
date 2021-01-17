package com.example.roomnodoappjava;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomnodoappjava.model.NoDo;
import com.example.roomnodoappjava.model.NoDoViewModel;
import com.example.roomnodoappjava.ui.NoDoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NoDoAdapter.ItemClickListener {

    private static final int NEW_NODO_REQUEST_CODE = 1;
    private NoDoAdapter noDoAdapter;
    private RecyclerView recyclerView;
    private NoDoViewModel noDoViewModel;
    private ItemTouchHelper itemTouchHelper;

    private int firstPos, lastPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*********************************************/
        //My Stuff

        noDoViewModel = new ViewModelProvider(this).get(NoDoViewModel.class);

        //Setting up recycler view
        recViewInit();

        noDoViewModel.getNoDoList().observe(this, new Observer<List<NoDo>>() {
            @Override
            public void onChanged(List<NoDo> noDos) {
                //update the saved copy of nodolist in the adapter
                noDoAdapter.setNoDos(noDos);
            }
        });


        /*********************************************/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNodoCreate(-420, new NoDo());
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void recViewInit() {
        recyclerView = findViewById(R.id.rec_view);
        noDoAdapter = new NoDoAdapter(this, this);
//        ItemTouchHelper.Callback callback = new NodoTouchHelper(this);
//        itemTouchHelper = new ItemTouchHelper(callback);
//        noDoAdapter.setTouchHelper(itemTouchHelper);
//        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setItemViewCacheSize(10);

        recyclerView.setAdapter(noDoAdapter);
//        found better design solution
//        divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(divider);

//        just the swipe, I implemented the swipe plus drag solution
        ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                NoDo nodo = noDoAdapter.getNoDo(viewHolder.getAdapterPosition());
                noDoViewModel.deleteNodo(nodo);
                Snackbar.make(recyclerView, "Item removed", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        noDoViewModel.insert(nodo);
                    }
                }).show();
            }
        };
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        firstPos = -4;

        ItemTouchHelper itemHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();


                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        NoDo fromNoDo = noDoAdapter.getNoDo(i);
                        NoDo toNoDo = noDoAdapter.getNoDo(i + 1);
                        int fromOrder = fromNoDo.getOrder();
                        fromNoDo.setOrder(toNoDo.getOrder());
                        toNoDo.setOrder(fromOrder);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        NoDo fromNoDo = noDoAdapter.getNoDo(i);
                        NoDo toNoDo = noDoAdapter.getNoDo(i - 1);
                        int fromOrder = fromNoDo.getOrder();
                        fromNoDo.setOrder(toNoDo.getOrder());
                        toNoDo.setOrder(fromOrder);
                    }
                }

                if(firstPos == -4){
                    firstPos = fromPosition;
                }
                lastPos = toPosition;

                noDoAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noDoViewModel.deleteNodo(noDoAdapter.getNoDo(viewHolder.getAdapterPosition()));
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);

                if(firstPos < lastPos) {
                    for (int i = firstPos; i < lastPos; i++) {
                        noDoViewModel.editNodo(noDoAdapter.getNoDo(i));
                    }
                }else{
                    for (int i = firstPos; i > lastPos; i--) {
                        noDoViewModel.editNodo(noDoAdapter.getNoDo(i));
                    }
                }


            }
        });

//        itemHelper.attachToRecyclerView(recyclerView);
    }

    private void goToNodoCreate(int id, NoDo noDo) {
        Intent intent = new Intent(MainActivity.this, NewNoDoActivity.class);
        intent.putExtra("nodoId", id);
        intent.putExtra("nodoTitle", noDo.getNoDoTitle());
        intent.putExtra("nodoDesc", noDo.getNoDoDesc());
        startActivityForResult(intent, NEW_NODO_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            noDoViewModel.deleteAll();
            return true;
        } else if (id == R.id.action_add_100) {
            int itemCount = noDoViewModel.getNoDoList().getValue().size();
            for (int i = itemCount; i < 100 + itemCount; i++) {
                int order = (noDoAdapter.getItemCount() > 0) ? noDoAdapter.getNoDo(noDoAdapter.getItemCount() - 1).getOrder() : 0;
                noDoViewModel.insert(new NoDo("NoDo#" + i, "Desc#" + i, order));
            }
            return true;
        }else if(id == R.id.action_update_list){
            //TODO: WHY DOESN'T THIS SHIT WORK
//            ArrayList<NoDo> listnodo = new ArrayList<>();
//            listnodo.add(new NoDo("0", "0", 0));
//            listnodo.add(new NoDo("1", "0", 1));
//            listnodo.add(new NoDo("2", "0", 2));
//            listnodo.add(new NoDo("3", "0", 3));
//            listnodo.add(new NoDo("4", "0", 4));
//            listnodo.add(new NoDo("5", "0", 5));
//            listnodo.add(new NoDo("6", "0", 6));
//            listnodo.add(new NoDo("7", "0", 7));
//            listnodo.add(new NoDo("8", "0", 8));
//            listnodo.add(new NoDo("9", "0", 9));
//            listnodo.add(new NoDo("10", "0", 10));
//            noDoAdapter.notifyDataSetChanged();
//            noDoAdapter.getNoDoList().clear();
//            noDoAdapter.getNoDoList().addAll(listnodo);

            noDoViewModel.getNoDoList().getValue().add(new NoDo("funci ", "shit", 23213));
            noDoViewModel.update(noDoViewModel.getNoDoList().getValue());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_NODO_REQUEST_CODE && resultCode == RESULT_OK) {
            //If the new nodo reply isn't empty create a new nodo if not edit the current one
            if (!TextUtils.isEmpty(data.getStringExtra(NewNoDoActivity.NEW_NODO_REPLY))) {

                int order = (noDoAdapter.getItemCount() > 0) ? noDoAdapter.getNoDo(noDoAdapter.getItemCount() - 1).getOrder() : 0;

                NoDo noDo = new NoDo(data.getStringExtra(NewNoDoActivity.NEW_NODO_REPLY), data.getStringExtra(NewNoDoActivity.NODO_DESC), order);
                noDoViewModel.insert(noDo);
            } else {
                //passing the whole NoDo because querying didn't work so I just used update instead in NoDoDao
                int id = data.getIntExtra(NewNoDoActivity.EDIT_NODO_REPLY_ID, 0);
                String text = data.getStringExtra(NewNoDoActivity.EDIT_NODO_REPLY_TEXT);
                String desc = data.getStringExtra(NewNoDoActivity.NODO_DESC);
                NoDo noDo = noDoAdapter.getNoDo(id);
                noDo.setNoDoTitle(text);
                noDo.setNoDoDesc(desc);
                noDoViewModel.editNodo(noDo);
            }
            return;
        }

        // If empty text is given back then this will be display, used return instead of else for readability
        Snackbar.make(recyclerView, "Empty cannot be saved", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }

    @Override
    public void OnItemClick(int id, NoDo noDo) {
        goToNodoCreate(id, noDo);
    }

}