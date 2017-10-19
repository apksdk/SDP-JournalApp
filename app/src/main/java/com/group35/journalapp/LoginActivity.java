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

/**
 * The type Login activity.
 * <p>
 * Created by Joshua on 9/18/2017
 */
public class LoginActivity extends AppCompatActivity {
    public static final String USERS = "users";
    public static final String EMAIL = "email";
    @BindView(R.id.usernameET)
    EditText usernameET;

    @BindView(R.id.passwordET)
    EditText passwordET;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    private ProgressDialog progressDialog;

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //Setup Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.sign_in_message));
        // Get current user
        FirebaseUser user = mAuth.getCurrentUser();
        //Check if the user is logged in
        if (user != null && !user.isAnonymous()) {
            //Skip login since user is logged in
            startActivity(new Intent(this, ViewJournalsActivity.class));
            finish();
        }
    }

    /**
     * Do initial setup
     */
    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is logged in
        if (mAuth.getCurrentUser() == null) {
            //Sign in anonymously
            mAuth.signInAnonymously();
        }
    }

    /**
     * Register handler.
     *
     * @param view the view
     */
    @OnClick(R.id.registerBTN)
    public void registerHandler(View view) {
        //Open register activity
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * Login handler.
     *
     * @param view the view
     */
    @OnClick(R.id.loginBTN)
    public void loginHandler(View view) {
        //Get user details
        final String[] username = {usernameET.getText().toString()};
        final String password = passwordET.getText().toString();
        //Check if username & pass is empty
        if (TextUtils.isEmpty(username[0]) || TextUtils.isEmpty(password)) {
            //Show error message
            Toast.makeText(this, R.string.missing_login_info_message, Toast.LENGTH_SHORT).show();
        } else {
            //Show login message
            progressDialog.show();
            //Check if the input is an email or username
            if (!Patterns.EMAIL_ADDRESS.matcher(usernameET.getText().toString()).matches()) {
                //Get database reference
                DatabaseReference ref = mDatabase.getReference();
                //Get user's email address
                ref.child(USERS).child(username[0]).child(EMAIL).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            //Get email
                            username[0] = dataSnapshot.getValue(String.class);
                            //Login
                            loginUser(username[0], password);
                        } else {
                            //Show error message as there's no matching emails
                            Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
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

    /**
     * Login with the provided username and password
     *
     * @param username The username
     * @param password The password
     */
    private void loginUser(String username, String password) {
        if(mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isAnonymous()) {
            mAuth.signOut();
        }
        //Sign in
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Check if the sign in is successful
                if (task.isSuccessful()) {
                    //Start activity
                    startActivity(new Intent(LoginActivity.this, ViewJournalsActivity.class));
                    finish();
                } else {
                    //Show login failed message
                    Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
