package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity<userpassword, username> extends AppCompatActivity {

    private EditText ename;
    private EditText epassword;
    private Button elogin;
    boolean isvalid=false;
    private TextView userRegistration;
    private String username="admin";
    private String userPassword="1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ename=(EditText)findViewById(R.id.editTextTextPersonName);
        epassword=(EditText)findViewById(R.id.editTextTextPassword);
        elogin=(Button)findViewById(R.id.button);
        userRegistration=(TextView)findViewById(R.id.textView4);

        elogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = ename.getText().toString();
                String inputpassword = epassword.getText().toString();
                if (inputName.isEmpty() || inputpassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter all the details correctly ", Toast.LENGTH_SHORT).show();
                } else {
                    isvalid = validate(inputName, inputpassword);
                    if (!isvalid) {
                        Toast.makeText(MainActivity.this, "Incorrect Username or Password ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Login is Succesful ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(MainActivity.this,Registerationactivity.class));
            }
        });
    }

    private boolean validate(String name, String password)
    {
        if(name.equals(username)&& password.equals(userPassword))
        {
            return true;
        }
        return false;
    }
}