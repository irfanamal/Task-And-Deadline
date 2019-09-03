package com.firzen.personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText deadlineEditText;
    private EditText descriptionEditText;
    private ImageButton datePickerDropdownButton;
    private Button saveButton;
    private Button cancelButton;
    private TaskViewModel viewModel;
    private int monthNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        titleEditText = findViewById(R.id.title_task);
        deadlineEditText = findViewById(R.id.deadline_task);
        descriptionEditText = findViewById(R.id.description_task);
        datePickerDropdownButton = findViewById(R.id.picker_button);
        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);
        viewModel = viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment(0);
                newFragment.show(getSupportFragmentManager(), getResources().getString(R.string.datePicker));
            }
        };

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
                    Task task = new Task(name, daySplits[0], Integer.parseInt(deadline[1]),
                            monthNum, Integer.parseInt(deadline[3]), description);
                    viewModel.insert(task);
                    finish();
                } else {
                    Toast.makeText(AddTaskActivity.this, "Title dan Deadline tidak boleh kosong",
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
