package com.example.vinoth.blogreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vinoth.blogreader.model.BlogPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BlogpostActivity extends AppCompatActivity {

    private static final int GALLERY=100;
    private ImageView pickimagebtn;
    private Uri Imageuri;
    private ProgressDialog mprogressDialog;

    StorageReference storageReference= FirebaseStorage.getInstance().getReference("image");
    DatabaseReference databaseReference;
    private EditText etTitle;
    private EditText etDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogpost);
        databaseReference= FirebaseDatabase.getInstance().getReference("datas");
        pickimagebtn=(ImageView)findViewById(R.id.ibtnadd);
        etTitle=(EditText)findViewById(R.id.etBlogTitel);
        etDesc=(EditText)findViewById(R.id.etdesc);
        mprogressDialog=new ProgressDialog(this);
    }

    public void pickupimage(View view) {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode==GALLERY)&&(resultCode==RESULT_OK)){
            Imageuri=data.getData();
            pickimagebtn.setImageURI(Imageuri);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploaddatas(View view) {
        mprogressDialog.setMessage("uploading.....");
        mprogressDialog.show();
        storageReference.child(Imageuri.getLastPathSegment()).putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadURI = taskSnapshot.getDownloadUrl();
                String dow= String.valueOf(downloadURI);
                DatabaseReference daRef = databaseReference.push();
                BlogPost blogPost =new BlogPost(etTitle.getText().toString(),etDesc.getText().toString(),dow);
                daRef.setValue(blogPost);
                mprogressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("failed","filll");
                Toast.makeText(getApplicationContext(),"Image failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}
