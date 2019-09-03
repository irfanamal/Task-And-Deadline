package com.firzen.personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText deadlineEditText;
    private EditText descriptionEditText;
    private ImageButton datePickerDropdownButton;
    private Button saveButton;
    private Button cancelButton;
    private ArrayList<Task> taskData;
    private TaskViewModel viewModel;
    private int monthNum;
    private Task currentTask;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleEditText = findViewById(R.id.edit_title_task);
        deadlineEditText = findViewById(R.id.edit_deadline_task);
        descriptionEditText = findViewById(R.id.edit_description_task);
        datePickerDropdownButton = findViewById(R.id.edit_picker_button);
        saveButton = findViewById(R.id.edit_save_button);
        cancelButton = findViewById(R.id.edit_cancel_button);
        taskData = new ArrayList<>();
        viewModel = viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_task", 0);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment(1);
                newFragment.show(getSupportFragmentManager(), getResources().getString(R.string.datePicker));
            }
        };

        viewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskData.clear();
                taskData.addAll(tasks);
                for (Task task : taskData) {
                    if (task.getId() == id) {
                        currentTask = task;
                        break;
                    }
                }
                titleEditText.setText(currentTask.getName());
                String[] months = getResources().getStringArray(R.array.month_names);
                deadlineEditText.setText(String.format(Locale.getDefault(),
                        "%s, %d %s %s",
                        currentTask.getDay(),
                        currentTask.getDate(),
                        months[currentTask.getMonthNum()-1],
                        currentTask.getYear()));
                descriptionEditText.setText(currentTask.getDescription());
            }
        });

        datePickerDropdownButton.setOnClickListener(onClickListener);
        deadlineEditText.setOnClickListener(onClickListener);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEditText.getText().toString().length() > 0 &&
                        deadlineEditText.getText().toString().length() > 0) {
                    String name = titleEditText.getText().toString();
                    String[] deadline = deadlineEditText.getText().toString().split(" ");
                    String[] daySplits = deadline[0].split(",");
                    String description = descriptionEditText.getText().toString();
                    currentTask.setName(name);
                    currentTask.setDay(daySplits[0]);
                    currentTask.setDate(Integer.parseInt(deadline[1]));
                    currentTask.setMonthNum(monthNum);
                    currentTask.setYear(Integer.parseInt(deadline[3]));
                    currentTask.setDescription(description);
                    viewModel.update(currentTask);
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Title dan Deadline tidak boleh kosong",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void processDatePickerResult(int year, int month, int day, int dayName) {
        String[] months = getResources().getStringArray(R.array.month_names);
        String[] days = getResources().getStringArray(R.array.day_names);
        String yearDisplay = String.valueOf(year);
        String monthDisplay = months[month];
        String dayDisplay = String.valueOf(day);
        String dayNameDisplay = days[dayName-1];

        deadlineEditText.setText(String.format("%s, %s %s %s", dayNameDisplay, dayDisplay, monthDisplay, yearDisplay));
        monthNum = month+1;
    }
}
