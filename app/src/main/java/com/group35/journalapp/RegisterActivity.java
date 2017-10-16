package com.group35.journalapp;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group35.journalapp.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The Register activity which users can use to create an account for the application.
 * <p>
 * Created by Joshua on 9/18/2017
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

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    /**
     * Register account.
     */
    @OnClick(R.id.registerBTN)
    public void registerAccount() {
        if (validateForm()) {
            //Initialize progress dialog
            final ProgressDialog saveProgressDialog = new ProgressDialog(this);
            saveProgressDialog.setMessage("Registering your account...");
            saveProgressDialog.setCancelable(false);
            saveProgressDialog.show();
            //Register account with email & pass
            mAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Check if registration is successful
                            if (task.isSuccessful()) {
                                //Update the user's display name
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(usernameET.getText().toString())
                                        .build();
                                mAuth.getCurrentUser().updateProfile(profileChangeRequest);
                                //Get database reference
                                DatabaseReference ref = mDatabase.getReference().child("users");
                                //Get current user
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String uID = currentUser.getUid();
                                //Create new user object
                                User user = new User(uID, emailET.getText().toString(), "");
                                //Save the user object in the database
                                ref.child(usernameET.getText().toString()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            saveProgressDialog.dismiss();
                                            Toast.makeText(getBaseContext(), "You have successfully registered & now logged in.", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getBaseContext(), ViewJournalsActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            } else {
                                //Show error for failed registration
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
        // Check if username is valid .', '#', '$', '[', or ']
        String invalidCharacters[] = {".", "#", "$", "[", "]"};
        for(String invalidChar : invalidCharacters) {
            if(usernameET.getText().toString().contains(invalidChar)) {
                validForm = false;
                usernameET.setError("Your username cannot contain the following characters: \n. # $ [ ]");
            }
        }

        return validForm;
    }
}
