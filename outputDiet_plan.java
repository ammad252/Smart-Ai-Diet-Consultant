package com.example.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class outputDiet_plan extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView dieplan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_diet_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Diet Plan");
        dieplan = findViewById(R.id.dietplan);

        // Retrieve prediction result from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", 0);
        String prediction = sharedPreferences.getString("prediction", "");

        // Set the prediction result to the TextView
        dieplan.setText(prediction);
// //////////////////////////////////////////////////////////////////////////////////////////////


    }
    //================Menu Item

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sidebar,menu);
        return  true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.feedback) {
            Intent i = new Intent(outputDiet_plan.this,Feedback.class);
            startActivity(i);
        }
//        if (id == R.id.healthy) {
//            Intent i = new Intent(outputDiet_plan.this,Viewpost.class);
//            startActivity(i);
//        }
        if (id == R.id.rf) {
            Intent i =new Intent(outputDiet_plan.this, Refresh.class);
            startActivity(i);
        }
        if(id == R.id.about){
            Intent i = new Intent(outputDiet_plan.this,about.class);
            startActivity(i);
        }
        if(id == R.id.cnt){
            Intent i = new Intent(outputDiet_plan.this,contactUs.class);
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