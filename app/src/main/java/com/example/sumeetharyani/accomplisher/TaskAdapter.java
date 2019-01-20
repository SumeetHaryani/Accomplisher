package com.example.sumeetharyani.accomplisher;


import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumeetharyani.accomplisher.data.TaskContract;
import com.example.sumeetharyani.accomplisher.data.TaskDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import at.markushi.ui.CircleButton;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Cursor mCursor = null;
    private Context mContext;
    public String LOG_TAG = "TaskAdapter";

    public TaskAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;

    }

    public void setData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    private String selectionArgs[] = null;

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTitle;
        public TextView taskPriority;
        public TextView taskCategory;
        public TextView taskDateTime;
        public CircleButton taskButton;

        public CardView cardView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_text);
            taskPriority = itemView.findViewById(R.id.priority_text);
            taskCategory = itemView.findViewById(R.id.category_text);
            taskDateTime = itemView.findViewById(R.id.dateTime_text);
            taskButton = itemView.findViewById(R.id.btn_task_complete);

            cardView = itemView.findViewById(R.id.card_view);

        }

        TaskDbHelper dbh = new TaskDbHelper(mContext);


    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new TaskViewHolder(view);
    }

    int id = -1;

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, final int position) {
        int pos = position;

        if (mCursor != null) {
            String title = null;
            String date;
            int priority = 1;
            long dateTime = 0;
            int category = 0;
            int color = 0;
            if (mCursor.moveToPosition(position)) {
                title = mCursor.getString(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_TITLE));
                dateTime = mCursor.getLong(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DATE));
                category = mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_CATEGORY));
                priority = mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_PRIORITY));
                color = mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_COLOR));

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCursor.moveToPosition(holder.getAdapterPosition());
                    id = mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry._ID));
                    Uri currentUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
                    Intent intent = new Intent(mContext, TaskEditorActivity.class);
                    intent.setData(currentUri);
                    mContext.startActivity(intent);
                }
            });
            if (color != 0) {
                holder.cardView.setCardBackgroundColor(color);
            } else {

                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.card));
            }

            holder.taskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Congratulations!, You Just Accomplished Your Task.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mCursor.moveToPosition(holder.getAdapterPosition());
                    id = mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry._ID));
                    Uri currentUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
                    mContext.getContentResolver().delete(currentUri, null, null);

                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());

                }
            });


            switch (category) {
                case 0:
                    holder.taskCategory.setText("Personal");
                    break;
                case 1:
                    holder.taskCategory.setText("Work");
                    break;
                case 2:
                    holder.taskCategory.setText("Education");
                    break;
            }
            switch (priority) {
                case 0:
                    holder.taskPriority.setText("Priority:High");
                    break;
                case 1:
                    holder.taskPriority.setText("Priority:Medium");
                    break;
                case 2:
                    holder.taskPriority.setText("Priority:Low");
                    break;
            }


            holder.taskTitle.setText(title);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();


            DateFormat dateFormat = DateFormat.getDateTimeInstance();


            date = dateFormat.format(dateTime);
            holder.taskDateTime.setText(date);


        } else {
            Log.e(LOG_TAG, "onBindViewHolder: Cursor is null.");
        }


    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return -1;
        }
    }
}



