package com.example.signup;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminDctView extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<User> list;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dct_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nutrition List");
        recyclerView = findViewById(R.id.userLis);
        database = FirebaseDatabase.getInstance().getReference("Nutrition");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        // Check for Internet Connection
        if (isConnected()) {

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        User user = dataSnapshot.getValue(User.class);
                        list.add(user);
                    }
                    myAdapter.notifyDataSetChanged();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    public void nutrition(View view) {
        Intent intent = new Intent(adminDctView.this,Nutrition.class);
        startActivity(intent);
        finish();
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
            Intent i =new Intent(adminDctView.this,CreateNAdmin.class);
            startActivity(i);
        }
        if (id == R.id.feedback) {
            Intent i =new Intent(adminDctView.this,Feedback.class);
            startActivity(i);
        }
        if (id == R.id.rf) {
            Intent i =new Intent(adminDctView.this, Refresh.class);
            startActivity(i);
        }
        if(id == R.id.about){
            Intent i = new Intent(adminDctView.this,about.class);
            startActivity(i);
        }
        if(id == R.id.cnt){
            Intent i = new Intent(adminDctView.this,contactUs.class);
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