package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.signup.databinding.ActivityFeedbackBinding;
import com.example.signup.databinding.ActivityPostactivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Postactivity extends AppCompatActivity {
    ActivityPostactivityBinding binding;
    String post;
    FirebaseDatabase db;
    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postactivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Articles");

        binding = ActivityPostactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post = binding.review.getText().toString();
                if(post.isEmpty()) {
                    Toast.makeText(Postactivity.this, "Please put your knowledge", Toast.LENGTH_SHORT).show();
                }
                // Check for Internet Connection
                if (isConnected()) {
                    if (!post.isEmpty()) {
                        postclass users = new postclass(post);
                        db = FirebaseDatabase.getInstance();
                        reference = db.getReference("post");
                        reference.child(post).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                Toast.makeText(Postactivity.this, "Successfully Posted", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    // Menu list
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menhu,menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signUpAdmin) {
            Intent i =new Intent(Postactivity.this,CreateNAdmin.class);
            startActivity(i);
        }
        if (id == R.id.feedback) {
            Intent i =new Intent(Postactivity.this,Feedback.class);
            startActivity(i);
        }
        if (id == R.id.rf) {
            Intent i =new Intent(Postactivity.this, Refresh.class);
            startActivity(i);
        }
        if(id == R.id.about){
            Intent i = new Intent(Postactivity.this,about.class);
            startActivity(i);
        }
        if(id == R.id.cnt){
            Intent i = new Intent(Postactivity.this,contactUs.class);
            startActivity(i);
        }
        if (id == R.id.logout) {
            Logout();
        }
        onBackPressed();
        return true;
    }

    private void Logout() {
        firebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}