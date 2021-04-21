package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Otpactivity extends AppCompatActivity {
    private Button btnlogic;
    private EditText ePhonenumber;
    private static final String TAG = "Otpactivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        btnlogic = findViewById(R.id.button4);
        ePhonenumber = findViewById(R.id.editTextNumberDecimal2);
        btnlogic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = ePhonenumber.getText().toString();
                if (phoneNumber.isEmpty())
                    Toast.makeText(Otpactivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                else {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91 "+ phoneNumber, 60, TimeUnit.SECONDS, Otpactivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                  signInUSer(phoneAuthCredential);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Log.d(TAG, "onVerificationFailed: " + e.getLocalizedMessage());
                                }

                                @Override
                                public void onCodeSent(final String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(verificationId, forceResendingToken);
                                    Dialog dialog = new Dialog(Otpactivity.this);
                                    dialog.setContentView(R.layout.verifyotp);
                                    EditText everifycode = dialog.findViewById(R.id.editTextNumberDecimal);
                                    Button btnverifycode = dialog.findViewById(R.id.button3);
                                    btnverifycode.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String verificationcode = everifycode.getText().toString();
                                            if (verificationId.isEmpty()) return;
                                            PhoneAuthCredential credential =PhoneAuthProvider.getCredential(verificationId,verificationcode);
                                            signInUSer(credential);
                                        }
                                    });
                                    dialog.show();
                                }
                            });
                }
            }
        });
    }

    private void signInUSer(PhoneAuthCredential credential) {
          FirebaseAuth.getInstance().signInWithCredential(credential)
                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){
                               startActivity(new Intent(Otpactivity.this,HomePageActivity.class));
                               finish();
                          }
                          else
                          {
                              Log.d(TAG, "onComplete: "+task.getException());
                          }
                      }
                  });
    }
}