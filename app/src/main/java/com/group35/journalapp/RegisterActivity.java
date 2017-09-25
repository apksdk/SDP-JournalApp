package com.group35.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The Register activity which users can use to create an account for the application.
 */
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerLayout)
    RelativeLayout registerLayout;

    @BindView(R.id.usernameET)
    EditText usernameET;

    @BindView(R.id.emailET)
    EditText emailET;

    @BindView(R.id.passwordET)
    EditText passwordET;

    @BindView(R.id.confirmPassET)
    EditText confirmPassET;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    /**
     * Register account.
     */
    @OnClick(R.id.registerBTN)
    public void registerAccount() {
        if (validateForm()) {
            mAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference ref = mDatabase.getReference().child("users");

                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String uID = currentUser.getUid();

                                User user = new User(usernameET.getText().toString(), emailET.getText().toString(), "");
                                ref.child(uID).setValue(user);

                                Toast.makeText(getBaseContext(), "You have successfully register & now logged in.", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getBaseContext(), ViewJournalsActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed, please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean validateForm() {
        boolean validForm = true;
        // Check if there's any empty fields
        for (int i = 0; i < registerLayout.getChildCount(); i++) {
            if (registerLayout.getChildAt(i) instanceof EditText) {
                EditText currentET = (EditText) registerLayout.getChildAt(i);
                if (TextUtils.isEmpty(currentET.getText().toString())) {
                    currentET.setError("This field cannot be empty!");
                    validForm = false;
                    break;
                }
            }
        }
        // Checks if the entered email is valid
        String email = emailET.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Please enter a valid email address!");
            validForm = false;
        }
        //Check if both passwords match & is at least 6 characters long
        String password = passwordET.getText().toString();
        String confirmPass = confirmPassET.getText().toString();
        if (!password.equals(confirmPass)) {
            confirmPassET.setError("Passwords do not match!");
            validForm = false;
        } else {
            if (password.length() < 6) {
                passwordET.setError("Password must have at least 6 characters!");
                validForm = false;
            }
        }
        return validForm;
    }
}
