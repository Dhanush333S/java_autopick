package com.example.autopick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class LiveActivity extends AppCompatActivity {

    TextView type,count;
    ImageView img;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String str=bundle.getString("name");
        type=findViewById(R.id.type);
        count=findViewById(R.id.count);
        img=findViewById(R.id.img);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(str);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                type.setText(snapshot.child("type").getValue().toString());
                count.setText(snapshot.child("count").getValue().toString());
                String data=snapshot.child("img").getValue().toString();

                storageReference= FirebaseStorage.getInstance().getReference().child(str+"/"+data+".jpg");
                Toast.makeText(LiveActivity.this, str+"/"+data, Toast.LENGTH_SHORT).show();
                try {
                    final File localFile=File.createTempFile("Screenshot (327)","jpg");
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(LiveActivity.this, "Picture Retrived", Toast.LENGTH_SHORT).show();
                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            img.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LiveActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}