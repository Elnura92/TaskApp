package com.androidlessons.taskapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlessons.taskapp.R;
import com.androidlessons.taskapp.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> list = new ArrayList<>();
    private MyClickListener clickListener;

    public void setClickListener(MyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        if (position % 2 == 0) {
            holder.textTitle.setBackgroundResource(R.color.grey);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(list.get(position).getTitle(), position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickListener.onItemLongClick(position);
                return true;
            }
        });



    }

    @Override
    public int getItemCount() {

        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);

        }

        public void bind(Task task) {
            textTitle.setText(task.getTitle());

        }


    }

    public void addItem(Task task) {
        list.add(task);
        notifyItemInserted(list.indexOf(task));
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }





    public interface MyClickListener{
        void onItemClick(String item, int position);

        void onItemLongClick(int position);
    }


}
