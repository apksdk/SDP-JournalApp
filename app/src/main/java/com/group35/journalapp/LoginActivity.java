package com.group35.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.usernameET)
    EditText usernameET;

    @BindView(R.id.passwordET)
    EditText passwordET;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(this, ViewJournalsActivity.class));
        }
    }

    @OnClick(R.id.registerBTN)
    public void registerHandler(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.loginBTN)
    public void loginUser(View view) {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Missing Login Information", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, ViewJournalsActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed, please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
