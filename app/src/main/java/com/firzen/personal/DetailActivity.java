package com.firzen.personal;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private TextView taskTitle;
    private TextView taskDeadline;
    private TextView taskDescription;
    private TaskViewModel viewModel;
    private ArrayList<Task> dataTasks;
    private Task currentTask;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskTitle = findViewById(R.id.detail_task_title);
        taskDeadline = findViewById(R.id.detail_task_deadline);
        taskDescription = findViewById(R.id.detail_task_description);
        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        dataTasks = new ArrayList<>();

        Intent intent = getIntent();
        id = intent.getIntExtra("id_task", 0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("id_task", id);
                startActivity(intent);
            }
        });

        viewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                dataTasks.clear();
                dataTasks.addAll(tasks);
                for (Task task : dataTasks) {
                    if (task.getId() == id) {
                        currentTask = task;
                        break;
                    }
                }
                taskTitle.setText(currentTask.getName());
                String[] months = getResources().getStringArray(R.array.month_names);
                taskDeadline.setText(String.format(Locale.getDefault(),
                        "%s, %d %s %s",
                        currentTask.getDay(),
                        currentTask.getDate(),
                        months[currentTask.getMonthNum()-1],
                        currentTask.getYear()));
                taskDescription.setText(currentTask.getDescription());
            }
        });
    }

}
