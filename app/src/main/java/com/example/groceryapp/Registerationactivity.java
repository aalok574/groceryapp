package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registerationactivity extends AppCompatActivity {

    private EditText eregname;
    private EditText eregpassword;
    private EditText eemail;
    private Button eregister;
    private TextView login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerationactivity);

        eregname = findViewById(R.id.editTextTextPersonName2);
        eemail = findViewById(R.id.editTextTextEmailAddress);
        eregpassword = findViewById(R.id.editTextTextPassword2);
        eregister = findViewById(R.id.button2);
        login = findViewById(R.id.textView3);
        firebaseAuth = FirebaseAuth.getInstance();
        eregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    //Upload data to the database
                    String user_email = eemail.getText().toString().trim();
                    String user_password = eregpassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Registerationactivity.this, "Registeration Succesful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registerationactivity.this,MainActivity.class));
                            } else {
                                Toast.makeText(Registerationactivity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registerationactivity.this, MainActivity.class));
            }
        });
    }
        private Boolean validate ()
        {
            Boolean result = false;
            String regUsername = eregname.getText().toString();
            String regEmail = eemail.getText().toString();
            String regUserPassword = eregpassword.getText().toString();
            if (regUsername.isEmpty() && regEmail.isEmpty() && regUserPassword.isEmpty()) {
                Toast.makeText(this, "Please Enter all the details the password should be atleast 8 charcacters", Toast.LENGTH_SHORT).show();

            } else {
                result = true;
            }
            return result;
        }

}