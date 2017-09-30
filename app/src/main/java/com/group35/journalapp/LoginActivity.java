package com.group35.journalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.usernameET)
    EditText usernameET;

    @BindView(R.id.passwordET)
    EditText passwordET;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.sign_in_message));

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && !user.isAnonymous()) {
            startActivity(new Intent(this, ViewJournalsActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            mAuth.signInAnonymously();
        }
    }

    @OnClick(R.id.registerBTN)
    public void registerHandler(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.loginBTN)
    public void loginHandler(View view) {
        final String[] username = {usernameET.getText().toString()};
        final String password = passwordET.getText().toString();
        if (TextUtils.isEmpty(username[0]) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Missing Login Information", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            if (!Patterns.EMAIL_ADDRESS.matcher(usernameET.getText().toString()).matches()) {
                DatabaseReference ref = mDatabase.getReference();
                ref.child("users").child(username[0]).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            username[0] = dataSnapshot.getValue(String.class);
                            loginUser(username[0], password);
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed, please try again.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                loginUser(username[0], password);
            }

        }
    }

    private void loginUser(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, ViewJournalsActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Authentication failed, please try again.", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
