package com.example.sumeetharyani.accomplisher.wallpaper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sumeetharyani.accomplisher.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;


public class WallpaperFragment extends Fragment {
    private static final int RC_PHOTO_PICKER = 2;
    private StorageReference wallpaperStorageReference;


    public WallpaperFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
        wallpaperStorageReference = mFirebaseStorage.getReference().child("wallpaper_photos");
        View motivationView = inflater.inflate(R.layout.fragment_wallpaper, container, false);
        WallpaperVerticalViewPager motivationVerticalViewPager = motivationView.findViewById(R.id.viewPager);
        motivationVerticalViewPager.setAdapter(new WallpaperVerticalViewPagerAdapter(getActivity()));
        return motivationView;

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_upload).setVisible(true);
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload:
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            RC_PHOTO_PICKER);

                } else {
                    chooseWallpaper();
                }
                return true;
            default:
                break;
        }
        return false;
    }

    @SuppressLint("IntentReset")
    private void chooseWallpaper() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, RC_PHOTO_PICKER);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_PHOTO_PICKER) {
                Uri returnUri = data.getData();
                uploadPhoto(returnUri);
            }
        }
    }

    private void uploadPhoto(Uri uri) {
        // Reset UI
        Toast.makeText(getContext(), "Uploading...", Toast.LENGTH_LONG).show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        // Upload to Cloud Storage
        final String uuid = UUID.randomUUID().toString();
        wallpaperStorageReference = FirebaseStorage.getInstance().getReference("userWallpapers/" + uuid);
        wallpaperStorageReference.putFile(uri).addOnSuccessListener((Activity) getContext(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageReference ref = taskSnapshot.getStorage();
                final String downloadLink = String.valueOf(ref);
                UploadDetails uploadDetails = null;
                if (user != null) {
                    uploadDetails = new UploadDetails(downloadLink, user.getDisplayName());
                }
                DatabaseReference pRef = null;
                if (user != null) {
                    pRef = myRef.child("usersUploads").child(user.getUid());
                }
                pRef.push().setValue(uploadDetails);
                Log.d(TAG, "uploadPhoto:onSuccess:" +
                        downloadLink);
                Toast.makeText(getContext(), "Image uploaded",
                        Toast.LENGTH_LONG).show();

            }
        })
                .addOnFailureListener((Activity) getContext(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "uploadPhoto:onError", e);
                        Toast.makeText(getContext(), "Upload failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
