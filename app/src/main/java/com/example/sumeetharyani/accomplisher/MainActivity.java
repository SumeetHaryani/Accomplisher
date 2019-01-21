package com.example.sumeetharyani.accomplisher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sumeetharyani.accomplisher.data.TaskContract;
import com.example.sumeetharyani.accomplisher.data.TaskDbHelper;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String ANONYMOUS = "anonymous";
    private static final int RC_SIGN_IN = 123;
    static FloatingActionButton fab;
    private SimpleFragmentPagerAdapter fragmentPagerAdapter;
    private SQLiteOpenHelper mDbHelper;
    private ViewPager mViewPager;
    private String mUsername;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = ANONYMOUS;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    onSignedIntialize(user.getDisplayName());
                    editor.putString("UID", user.getUid());
                    editor.apply();
                    Toast.makeText(MainActivity.this, "You are signed in " + mUsername, Toast.LENGTH_SHORT).show();
                } else {
                    editor.remove("UID");
                    editor.commit();
                    onSignedOutIntialize();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)

                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build())
                                    )

                                    .build(),
                            RC_SIGN_IN);
                }


            }
        };
        mDbHelper = new TaskDbHelper(this);
        fragmentPagerAdapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        fab.show();
                        break;
                    case 1:
                        fab.hide();
                        break;
                    case 3:
                        fab.hide();
                        break;
                    default:
                        fab.hide();
                        break;
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, TaskEditorActivity.class);
                startActivity(intent);
            }
        });
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        String[] projection = {TaskContract.TaskEntry.COLUMN_TASK_TITLE, TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, TaskContract.TaskEntry.COLUMN_TASK_CATEGORY,
                TaskContract.TaskEntry.COLUMN_TASK_PRIORITY, TaskContract.TaskEntry.COLUMN_TASK_DATE};


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.action_signOut):
                AuthUI.getInstance()
                        .signOut(this);
                return true;
            case (R.id.action_delete_all):
                getContentResolver().delete(TaskContract.TaskEntry.CONTENT_URI, null, null);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (requestCode == RESULT_OK) {

                Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

    }

    private void onSignedIntialize(String username) {
        mUsername = username;


    }

    private void onSignedOutIntialize() {
        mUsername = ANONYMOUS;

    }
}
