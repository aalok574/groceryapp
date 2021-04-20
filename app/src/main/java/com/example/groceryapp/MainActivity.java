package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity<userpassword, username> extends AppCompatActivity {

    private static final int RC_SIGN_IN =101 ;
    private EditText ename;
    private EditText epassword;
    private Button elogin;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ImageButton eimage;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ename = (EditText) findViewById(R.id.editTextTextPersonName);
        epassword = (EditText) findViewById(R.id.editTextTextPassword);
        elogin = (Button) findViewById(R.id.button);
        userRegistration = (TextView) findViewById(R.id.textView4);
        eimage = findViewById(R.id.imageButton);
        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
        }

        elogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(ename.getText().toString(), epassword.getText().toString());
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registerationactivity.class));
            }
        });

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
 eimage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signIn();
        }
    });
}
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, user.getEmail(),Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, task.getException().toString(),Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user)
    {
        Intent intent=new Intent(MainActivity.this,HomePageActivity.class);
        startActivity(intent);
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