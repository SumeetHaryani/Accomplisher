package com.example.sumeetharyani.accomplisher.tasks;

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

import com.example.sumeetharyani.accomplisher.R;
import com.example.sumeetharyani.accomplisher.data.TaskContract;
import com.example.sumeetharyani.accomplisher.data.TaskDbHelper;

@SuppressLint("ValidFragment")
public class TaskFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private TaskAdapter taskAdapter;
    private SQLiteOpenHelper hp;
    private Cursor cursor;


    @SuppressLint("ValidFragment")
    public TaskFragment() {
    }

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
        RecyclerView recyclerView = taskView.findViewById(R.id.taskList);
        taskAdapter = new TaskAdapter(getContext(), cursor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdapter);
        return taskView;

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COLUMN_TASK_TITLE, TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, TaskContract.TaskEntry.COLUMN_TASK_CATEGORY,
                TaskContract.TaskEntry.COLUMN_TASK_PRIORITY, TaskContract.TaskEntry.COLUMN_TASK_DATE, TaskContract.TaskEntry.COLUMN_TASK_COLOR, TaskContract.TaskEntry.COLUMN_TASK_UID};
//        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        String UID = prefs.getString("UID", null);
//        String selection= TaskContract.TaskEntry.COLUMN_TASK_UID+"=?";
//        String[] selectionArg= new String []{UID};
//        if (UID!=null){
//            Log.d(TAG, "*123");
//        return new CursorLoader(getActivity(),
//                TaskContract.TaskEntry.CONTENT_URI,
//                projection,
//                selection,
//                selectionArg,
//                TaskContract.TaskEntry.COLUMN_TASK_PRIORITY + " ASC");}
//                else
        return new CursorLoader(getActivity(),
                TaskContract.TaskEntry.CONTENT_URI,
                projection,
                null,
                null,
                TaskContract.TaskEntry.COLUMN_TASK_PRIORITY + " ASC");

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        taskAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        taskAdapter.swapCursor(null);
    }

}

