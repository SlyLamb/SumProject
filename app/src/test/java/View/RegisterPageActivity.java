package View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.slylamb.pocketcuisine.R;

import Presenter.RegisterPageActivityPresenter;

//implements
//        View.OnClickListener
public class RegisterPageActivity extends AppCompatActivity implements RegisterPageActivityPresenter.View {



    private RegisterPageActivityPresenter presenter;
    private EditText mEmailField;
    private EditText mPasswordField;

    // [START declare_auth]
    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPageActivityPresenter(this);



        // Views
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        // Buttons
       // findViewById(R.id.submit).setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }



//    private void signOut() {
//        mAuth.signOut();
//        updateUI(null);
//    }
//
//    private void sendEmailVerification() {
//        // Disable button
//        findViewById(R.id.verifyEmailButton).setEnabled(false);
//
//        // Send verification email
//        // [START send_email_verification]
//        final FirebaseUser user = mAuth.getCurrentUser();
//        user.sendEmailVerification()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // [START_EXCLUDE]
//                        // Re-enable button
//                        findViewById(R.id.verifyEmailButton).setEnabled(true);
//
//                        if (task.isSuccessful()) {
//                            Toast.makeText(EmailPasswordActivity.this,
//                                    "Verification email sent to " + user.getEmail(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.e(TAG, "sendEmailVerification", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this,
//                                    "Failed to send verification email.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END send_email_verification]
//    }
//

//
//    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
//        if (user != null) {
//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
//                    user.getEmail(), user.isEmailVerified()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
//            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
//            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());
//        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
//            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
//            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.emailCreateAccountButton) {
//            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
//        } else if (i == R.id.emailSignInButton) {
//            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
//        } else if (i == R.id.signOutButton) {
//            signOut();
//        } else if (i == R.id.verifyEmailButton) {
//            sendEmailVerification();
//        }
//    }
}