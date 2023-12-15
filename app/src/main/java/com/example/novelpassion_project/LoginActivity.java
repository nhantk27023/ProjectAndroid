package com.example.novelpassion_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText Ed_user, Ed_password;
    Button btn_login;
    ProgressBar progressBar;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        progressBar.setVisibility(View.GONE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Ed_user = findViewById(R.id.user);
        Ed_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registerNow);

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                progressBar.setVisibility(View.VISIBLE);
                if(!validateEmail() | !validatePassword())
                {
                }
                else
                {
                    checkEmail();
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean validateEmail()
    {
        String val = Ed_user.getText().toString();
        if(val.isEmpty())
        {
            Ed_user.setError("User cannot be empty");
            return false;
        }
        else {
            Ed_user.setError(null);
            return true;
        }
    }

    public boolean validatePassword()
    {
        String val = Ed_password.getText().toString();
        if(val.isEmpty())
        {
            Ed_password.setError("Password cannot be empty");
            return false;
        }
        else {
            Ed_password.setError(null);
            return true;
        }
    }



    public void checkEmail()
    {
        String username = Ed_user.getText().toString().trim();
        String password = Ed_password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("user").equalTo(username);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Ed_user.setError(null);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);

                    if(Objects.equals(passwordFromDB, password))
                    {
                        Ed_user.setError(null);
                        Intent intent = new Intent(LoginActivity.this, HomePage.class);
                        intent.putExtra("USERNAME", username); // Thêm tên người dùng vào Intent
                        startActivity(intent);
                    }
                    else
                    {
                        Ed_password.setError("Invalid Credentials");
                        Ed_password.requestFocus();
                    }
                }
                else
                {
                    Ed_user.setError("User does not exist");
                    Ed_user.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

