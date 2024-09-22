package com.example.cameraapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView profileIv;
    Button cameraBtn, galleryBtn, videoCameraBtn, videoGalleryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileIv = findViewById(R.id.profile_iv);
        cameraBtn = findViewById(R.id.camera_btn);
        galleryBtn = findViewById(R.id.gallery_btn);
        //videoCameraBtn = findViewById(R.id.video_camera_btn);
        //videoGalleryBtn = findViewById(R.id.video_gallery_btn);

        cameraBtn.setOnClickListener(view -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResultLauncher.launch(cameraIntent);
        });

        galleryBtn.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryResultLauncher.launch(galleryIntent);
        });

        videoCameraBtn.setOnClickListener(view -> {
            Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoCameraResultLauncher.launch(videoIntent);
        });

        videoGalleryBtn.setOnClickListener(view -> {
            Intent videoGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            videoGalleryResultLauncher.launch(videoGalleryIntent);
        });
    }

    ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap avatar = (Bitmap) Objects.requireNonNull(result.getData().getExtras()).get("data");
                        profileIv.setImageBitmap(avatar);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        profileIv.setImageURI(imageUri);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> videoCameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri videoUri = result.getData().getData();
                        Toast.makeText(MainActivity.this, "Видео снято: " + Objects.requireNonNull(videoUri), Toast.LENGTH_LONG).show();
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> videoGalleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri videoUri = result.getData().getData();
                        Toast.makeText(MainActivity.this, "Выбрано видео: " + Objects.requireNonNull(videoUri), Toast.LENGTH_LONG).show();
                    }
                }
            }
    );
}
