package com.example.sumeetharyani.accomplisher;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumeetharyani.accomplisher.data.TaskContract;
import com.example.sumeetharyani.accomplisher.data.TaskDbHelper;

@SuppressLint("ValidFragment")
public class TaskFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    private Cursor cursor;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @SuppressLint("ValidFragment")
    public TaskFragment() {

    }

    SQLiteOpenHelper hp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hp = new TaskDbHelper(getContext());


        getActivity().getSupportLoaderManager().initLoader(0, null, this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View taskView = inflater.inflate(R.layout.fragment_task, container, false);
        recyclerView = (RecyclerView) taskView.findViewById(R.id.taskList);
        taskAdapter = new TaskAdapter(getContext(), cursor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(taskAdapter);


        return taskView;

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COLUMN_TASK_TITLE, TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, TaskContract.TaskEntry.COLUMN_TASK_CATEGORY,
                TaskContract.TaskEntry.COLUMN_TASK_PRIORITY, TaskContract.TaskEntry.COLUMN_TASK_DATE, TaskContract.TaskEntry.COLUMN_TASK_COLOR};

        return new CursorLoader(getActivity(),
                TaskContract.TaskEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        taskAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        taskAdapter.setData(null);
    }
}

