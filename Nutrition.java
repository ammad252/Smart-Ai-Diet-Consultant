package com.example.signup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.signup.databinding.ActivityNutritionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Nutrition extends AppCompatActivity {
    ActivityNutritionBinding binding;
    String firstName,lastname,phoneno,userName;
    FirebaseDatabase db;
    DatabaseReference reference;
    NotificationManagerCompat notificationManagerCompat,notificationManagerCompat1;
    Notification notification,notification1;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Dietitian");
        binding = ActivityNutritionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //-----------When Nutritionist added Notification Show----------------------

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("mynotification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, "mynotification").setContentText("add successfully").setAutoCancel(true)
                .setContentText("Nutrition added successfully").setSmallIcon(android.R.drawable.stat_notify_sync);
        notification = builder.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        //--------------------------------------------------------------------------------------


        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = binding.firstName.getText().toString();
                lastname = binding.lastName.getText().toString();
                phoneno = binding.phon.getText().toString();
                userName = binding.stat.getText().toString();

                if(firstName.isEmpty() && lastname.isEmpty() && phoneno.isEmpty() && userName.isEmpty()){
                    Toast.makeText(Nutrition.this, "Please fill the form", Toast.LENGTH_SHORT).show();
                }
                else if(firstName.isEmpty() && !lastname.isEmpty() && !phoneno.isEmpty() && !userName.isEmpty()){
                    Toast.makeText(Nutrition.this, "Please add your first name", Toast.LENGTH_SHORT).show();
                }
                else if(!firstName.isEmpty() && lastname.isEmpty() && !phoneno.isEmpty() && !userName.isEmpty()){
                    Toast.makeText(Nutrition.this, "Please add your last name", Toast.LENGTH_SHORT).show();
                }
                else if(!firstName.isEmpty() && !lastname.isEmpty() && phoneno.isEmpty() && !userName.isEmpty()){
                    Toast.makeText(Nutrition.this, "Please add your Mobile number", Toast.LENGTH_SHORT).show();
                }
                else if(!firstName.isEmpty() && !lastname.isEmpty() && !phoneno.isEmpty() && userName.isEmpty()){
                    Toast.makeText(Nutrition.this, "Please add your Availability status", Toast.LENGTH_SHORT).show();
                }
                else if(!firstName.isEmpty() && !lastname.isEmpty() && !phoneno.isEmpty() && !userName.isEmpty()) {
                    // Check for Internet Connection
                    if (isConnected()) {

                    User users = new User(firstName, lastname, phoneno, userName);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Nutrition");
                    reference.child(firstName).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            binding.firstName.setText("");
                            binding.lastName.setText("");
                            binding.phon.setText("");
                            binding.stat.setText("");

                            Toast.makeText(Nutrition.this, "Nutrition added Successfully", Toast.LENGTH_SHORT).show();
                            notificationManagerCompat.notify(1,notification);

                        }
                    });
                }else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


//        binding.update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String fisrtName = binding.firstName.getText().toString();
//                String lastName = binding.lastName.getText().toString();
//                String phono = binding.phon.getText().toString();
//                String  staus = binding.userName.getText().toString();
//
//
//                updatedata(fisrtName,lastName,phono,staus);
//
//            }
//
//        });
//    }
//
//    private void updatedata( String firstName, String lastName, String phno,String status) {
//
//        HashMap User = new HashMap();
//        User.put("lastName",lastName);
//        User.put("phone",phno);
//        User.put("status",status);
//
//        reference = FirebaseDatabase.getInstance().getReference("Nutrition");
//        reference.child(firstName).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//
//                if (task.isSuccessful()){
//
//                    binding.userName.setText("");
//                    binding.firstName.setText("");
//                    binding.lastName.setText("");
//                    binding.phon.setText("");
//                    Toast.makeText(Nutrition.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
//
//                }else {
//
//                    Toast.makeText(Nutrition.this,"Failed to Update",Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });
//
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.firstName.getText().toString();
                if (!username.isEmpty()){

                    deleteData(username);

                }else{

                    Toast.makeText(Nutrition.this,"Please Enter Username",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
//
        private void deleteData(String username) {

            reference = FirebaseDatabase.getInstance().getReference("Nutrition");
            reference.child(firstName).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){

                        Toast.makeText(Nutrition.this,"Successfuly Deleted",Toast.LENGTH_SHORT).show();
                        binding.firstName.setText("");


                    }else {

                        Toast.makeText(Nutrition.this,"Failed",Toast.LENGTH_SHORT).show();


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
            Intent i =new Intent(Nutrition.this,CreateNAdmin.class);
            startActivity(i);
        }
        if (id == R.id.feedback) {
            Intent i =new Intent(Nutrition.this,Feedback.class);
            startActivity(i);
        }
        if (id == R.id.rf) {
            Intent i =new Intent(Nutrition.this, Refresh.class);
            startActivity(i);
        }
        if(id == R.id.about){
            Intent i = new Intent(Nutrition.this,about.class);
            startActivity(i);
        }
        if(id == R.id.cnt){
            Intent i = new Intent(Nutrition.this,contactUs.class);
            startActivity(i);
        }
        if (id == R.id.logout) {
            Logout();
        }
        onBackPressed();
        return true;
    }

    private void Logout() {
//        firebaseAuth.signOut();
        firebaseAuth.getInstance().signOut();
        finish();

    }
    }