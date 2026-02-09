package com.example.signup;

import android.annotation.SuppressLint;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminPostView extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    my_Adapter myAdapter;
    ArrayList<postclass> list;
    private FloatingActionButton post;
    private FirebaseAuth firebaseAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_view);
        getSupportActionBar().setTitle("Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        post = findViewById(R.id.pst);


        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("post");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new my_Adapter(this,list);
        recyclerView.setAdapter(myAdapter);

        if (isConnected()) {

            database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    postclass user = dataSnapshot.getValue(postclass.class);
                    list.add(user);
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminPostView.this, Postactivity.class);
                startActivity(intent);
                finish();
            }
        });
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
            Intent i =new Intent(adminPostView.this,CreateNAdmin.class);
            startActivity(i);
        }
        if (id == R.id.feedback) {
            Intent i =new Intent(adminPostView.this,Feedback.class);
            startActivity(i);
        }
        if (id == R.id.rf) {
            Intent i =new Intent(adminPostView.this, Refresh.class);
            startActivity(i);
        }
        if(id == R.id.about){
            Intent i = new Intent(adminPostView.this,about.class);
            startActivity(i);
        }
        if(id == R.id.cnt){
            Intent i = new Intent(adminPostView.this,contactUs.class);
            startActivity(i);
        }
        if (id == R.id.logout) {
            Logout();
        }
        onBackPressed();
        return  true;
    }

    private void Logout() {
        firebaseAuth.getInstance().signOut();
        finish();
    }

}