package com.example.gilho.images;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        // access your storage bucket instance
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // create a reference
        StorageReference storageRef = storage.getReference();
        // some child reference --> ref now points to "images" which I cannot find
        StorageReference putBytesRef = storageRef.child("putBytes.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        putBytesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // handle byte data
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // display two images already saved on resources
                ImageView imageOne = (ImageView) findViewById(R.id.imageone);
                ImageView imageTwo = (ImageView)findViewById(R.id.imagetwo);

                imageOne.setImageBitmap(bitmap);
                imageTwo.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // handle errors
            }
        });









    }

}
