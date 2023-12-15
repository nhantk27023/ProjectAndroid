package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText Ed_user, Ed_email, Ed_password;
    Button btn_register;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        progressBar.setVisibility(View.GONE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Ed_user = findViewById(R.id.user);
        Ed_email = findViewById(R.id.email);
        Ed_password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        btn_register.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(View.VISIBLE);

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String user = Ed_user.getText().toString();
                String email = Ed_email.getText().toString();
                String password = Ed_password.getText().toString();

                HelperClass helperClass = new HelperClass(user, email, password);
                reference.child(user).setValue(helperClass);

                Toast.makeText(RegisterActivity.this, "Acccount successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
//              finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }
}