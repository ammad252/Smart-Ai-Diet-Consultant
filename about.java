package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class about extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Us");
    }
    //================Menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sidebar,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.feedback) {
            Intent i = new Intent(about.this,Feedback.class);
            startActivity(i);
      }
//        if (id == R.id.healthy) {
//            Intent i = new Intent(about.this,Viewpost.class);
//            startActivity(i);
//        }
        if (id == R.id.rf) {
            Intent i =new Intent(about.this, Refresh.class);
            startActivity(i);
        }
        if(id == R.id.about){
            Intent i = new Intent(about.this,about.class);
            startActivity(i);
        }
        if(id == R.id.cnt){
            Intent i = new Intent(about.this,contactUs.class);
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
}