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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity<userpassword, username> extends AppCompatActivity {

    private EditText ename;
    private EditText epassword;
    private Button elogin;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ename=(EditText)findViewById(R.id.editTextTextPersonName);
        epassword=(EditText)findViewById(R.id.editTextTextPassword);
        elogin=(Button)findViewById(R.id.button);
        userRegistration=(TextView)findViewById(R.id.textView4);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user= firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,HomePageActivity.class));
        }

        elogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(ename.getText().toString(),epassword.getText().toString());
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(MainActivity.this,Registerationactivity.class));
            }
        });
    }

    private void validate(String name, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(name,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
          if(task.isSuccessful())
          {
              Toast.makeText(MainActivity.this, "Login is Succesful ", Toast.LENGTH_SHORT).show();
              startActivity(new Intent(MainActivity.this,HomePageActivity.class));
          }
          else
          {
              Toast.makeText(MainActivity.this, "Login is not  Succesful ", Toast.LENGTH_SHORT).show();
          }
            }
        });
    }
}