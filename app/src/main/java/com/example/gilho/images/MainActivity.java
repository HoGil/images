package com.example.gilho.images;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        // display two images already saved on resources
        ImageView imageOne = (ImageView) findViewById(R.id.imageone);
        ImageView imageTwo = (ImageView)findViewById(R.id.imagetwo);
        int imageResource = getResources().getIdentifier("@drawable/header_image", null, this.getPackageName());

        imageOne.setImageResource(imageResource);
        imageTwo.setImageResource(imageResource);

        // access your storage bucket instance
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // create a reference
        StorageReference storageRef = storage.getReference();
        // some child reference --> ref now points to "images" which I cannot find
        //StorageReference putBytesRef = storageRef.child("putBytes.jpg");
        // child references can also take paths
        // spaceRef now points to "images/space.jpg"
        // imagesRef still points to "images"
        StorageReference putBytesImagesRef = storageRef.child("images/putBytes.jpg");

        // Upload Files
        // lets try putBytes first

        // get the data from an ImageView as bytes first
        imageOne.setDrawingCacheEnabled(true);
        imageOne.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable)imageOne.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // do the uploading
        UploadTask uploadTask = putBytesImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // handler unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata();
            }
        });


    }

}
