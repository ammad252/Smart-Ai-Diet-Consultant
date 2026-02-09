package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateNAdmin extends AppCompatActivity {

    private EditText username,password,Cpassword;
    private Button register_btn;
//    private TextView Login;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fypproject-256f3-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_nadmin);
        getSupportActionBar().setTitle("Create New Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username=findViewById(R.id.username);
        password=findViewById(R.id.passwd);
        Cpassword=findViewById(R.id.cpassword);
        register_btn=findViewById(R.id.reg);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = username.getText().toString();
                String Pass = password.getText().toString();
                String confirmpassword = Cpassword.getText().toString();

                if(UserName.isEmpty() && Pass.isEmpty() && confirmpassword.isEmpty()){
                    Toast.makeText(CreateNAdmin.this, "Please add your credentials", Toast.LENGTH_SHORT).show();
                }
                else if(!UserName.isEmpty() && Pass.isEmpty() && confirmpassword.isEmpty()){
                    Toast.makeText(CreateNAdmin.this, "Please add Password & Confirm it", Toast.LENGTH_SHORT).show();
                }
                else if(UserName.isEmpty() && !Pass.isEmpty() && !confirmpassword.isEmpty()){
                    Toast.makeText(CreateNAdmin.this, "Please add UserName", Toast.LENGTH_SHORT).show();
                }
                else if(!UserName.isEmpty() && Pass.isEmpty() && !confirmpassword.isEmpty()){
                    Toast.makeText(CreateNAdmin.this, "Please add Password", Toast.LENGTH_SHORT).show();
                }
                else if(!UserName.isEmpty() && !Pass.isEmpty() && confirmpassword.isEmpty()){
                    Toast.makeText(CreateNAdmin.this, "Please add Confirm password", Toast.LENGTH_SHORT).show();
                }
                else if (!Pass.equals(confirmpassword)){
                    Toast.makeText(CreateNAdmin.this, "Confirm password not matched", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Admin").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(UserName)){
                                Toast.makeText(CreateNAdmin.this, "UserName already exist", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("Admin").child(UserName).child("password").setValue(Pass);
                                databaseReference.child("Admin").child(UserName).child("confirm_password").setValue(confirmpassword);

                                Toast.makeText(CreateNAdmin.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent i =new Intent(CreateNAdmin.this, CreateNAdmin.class);
                                startActivity(i);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        onBackPressed();
        return true;
    }
}