package com.firzen.personal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Task> tasks;

    public ItemAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bindTo(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView deadline;
        private Task currentTask;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            deadline = itemView.findViewById(R.id.task_deadline);
            itemView.setOnClickListener(this);
        }

        public void bindTo(Task task) {
            currentTask = task;
            String[] months = context.getResources().getStringArray(R.array.month_names);
            title.setText(task.getName());
            deadline.setText(String.format(Locale.getDefault(),
                    "%s, %d %s %d", task.getDay(), task.getDate(), months[task.getMonthNum()-1], task.getYear()));
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id_task", currentTask.getId());
            context.startActivity(intent);
        }
    }
}
