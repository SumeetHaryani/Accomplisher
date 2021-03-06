package com.example.sumeetharyani.accomplisher;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sumeetharyani.accomplisher.data.TaskContract;

import java.text.DateFormat;
import java.util.Calendar;

import petrov.kristiyan.colorpicker.ColorPicker;

public class TaskEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final int EXISTING_TASK_LOADER = 0;
    private final String TAG = TaskEditorActivity.class.getName();
    private Button colorButton;
    private Spinner categorySpinner;
    private Spinner prioritySpinner;
    private EditText taskTitle;
    private EditText taskDescription;
    private int itemColor;
    private int prioritySpinnerPosition;
    private int categorySpinnerPosition;
    private long dateTime;
    private Uri mCurrentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        categorySpinner = findViewById(R.id.spinner_category);
        prioritySpinner = findViewById(R.id.spinner_priority);
        taskTitle = findViewById(R.id.edit_text_title);
        taskDescription = findViewById(R.id.edit_text_description);
        Intent intent = getIntent();
        mCurrentUri = intent.getData();
        if (mCurrentUri != null) {
            setTitle("Edit Task");
            getLoaderManager().initLoader(EXISTING_TASK_LOADER, null, this);
        }
        colorButton = findViewById(R.id.color_button);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPicker();

            }

        });
        final Intent intent2 = new Intent(TaskEditorActivity.this, MainActivity.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateTime();
                saveToDb();
                startActivity(intent2);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);
        prioritySpinner.setOnItemSelectedListener(this);
    }


    private void saveToDb() {
        String title = String.valueOf(taskTitle.getText());
        String description = String.valueOf(taskDescription.getText());
        prioritySpinnerPosition = prioritySpinner.getSelectedItemPosition();
        categorySpinnerPosition = categorySpinner.getSelectedItemPosition();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String UID = prefs.getString("UID", null);
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TASK_TITLE, title);
        values.put(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, description);
        values.put(TaskContract.TaskEntry.COLUMN_TASK_PRIORITY, prioritySpinnerPosition);
        values.put(TaskContract.TaskEntry.COLUMN_TASK_CATEGORY, categorySpinnerPosition);
        values.put(TaskContract.TaskEntry.COLUMN_TASK_DATE, dateTime);
        values.put(TaskContract.TaskEntry.COLUMN_TASK_COLOR, itemColor);
        values.put(TaskContract.TaskEntry.COLUMN_TASK_UID, UID);
        if (!title.isEmpty() && mCurrentUri == null) {
            getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, values);

        } else if (!title.isEmpty()) {
            getContentResolver().update(mCurrentUri, values, null, null);
        }
    }


    private void colorPicker() {
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.setRoundColorButton(true);
        colorPicker.setTitle("Select Color For Task");
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                itemColor = color;
                colorButton.setBackgroundColor(itemColor);
            }

            @Override
            public void onCancel() {
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spinner_category) {
            categorySpinnerPosition = pos;
        } else if (spinner.getId() == R.id.spinner_priority) {
            prioritySpinnerPosition = pos;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback no colour selected
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            if (mCurrentUri != null) {
                int rowsDeleted = getContentResolver().delete(mCurrentUri, null, null);
                Log.d(TAG, "onOptionsItemSelected: " + rowsDeleted);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDateTime() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        dateTime = calendar.getTimeInMillis();
        String todayDateTimeString = dateFormat.format(dateTime);
        Log.d(TAG, todayDateTimeString);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COLUMN_TASK_TITLE, TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, TaskContract.TaskEntry.COLUMN_TASK_CATEGORY,
                TaskContract.TaskEntry.COLUMN_TASK_PRIORITY, TaskContract.TaskEntry.COLUMN_TASK_DATE, TaskContract.TaskEntry.COLUMN_TASK_COLOR};
        return new CursorLoader(this,
                mCurrentUri,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor mCursor) {
        if (mCursor == null || mCursor.getCount() < 1) {
            return;
        }
        if (mCursor.moveToFirst()) {
            String title = mCursor.getString(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_TITLE));
            String description = mCursor.getString(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION));
            int category = mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_CATEGORY));
            int priority = mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_PRIORITY));
            itemColor = mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_COLOR));
            if (itemColor == 0) {
                colorButton.setBackgroundColor(getResources().getColor(R.color.card));
            } else {
                colorButton.setBackgroundColor(itemColor);
            }
            categorySpinner.setSelection(category);
            prioritySpinner.setSelection(priority);
            taskTitle.setText(title);
            taskDescription.setText(description);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        colorButton.setBackgroundColor(getResources().getColor(R.color.card));
        categorySpinner.setSelection(0);
        prioritySpinner.setSelection(0);
        taskTitle.setText("");
        taskDescription.setText("");
    }
}
