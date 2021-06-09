package com.example.lab_08_android_firebase_mood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MoodActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnHappy, btnUnhappy, btnNormal;
    private Button btnFinish;
    private String userId;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        btnHappy = findViewById(R.id.btnHappy);
        btnUnhappy = findViewById(R.id.btnUnhappy);
        btnNormal = findViewById(R.id.btnNormal);
        btnFinish = findViewById(R.id.btnFinish);
        db = FirebaseFirestore.getInstance();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userId = null;
            } else {
                userId= extras.getString("USERID");
            }
        } else {
            userId= (String) savedInstanceState.getSerializable("USERID");
        }

        documentReference = db.collection("Users").document(userId);
        getData();

        btnHappy.setOnClickListener(this);
        btnUnhappy.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    private void getData(){
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHappy:{
                int happy = user.getHappy() + 1;
                user.setHappy(happy);
                Toast.makeText(MoodActivity.this, "Happy :)", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnUnhappy:{
                int unhappy = user.getUnhappy() + 1;
                user.setUnhappy(unhappy);
                Toast.makeText(MoodActivity.this, "Unhappy :(", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnNormal:{
                int normal = user.getNormal() + 1;
                user.setNormal(normal);
                Toast.makeText(MoodActivity.this, "Normal", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnFinish:{
                documentReference.update(
                        "happy", user.getHappy(),
                        "unhappy", user.getUnhappy(),
                        "normal", user.getNormal()
                );
                Toast.makeText(MoodActivity.this, "Update successful!!!", Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
}