package com.example.realestateapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.realestateapp.Modeles.Agent;
import com.example.realestateapp.Modeles.Asset;
import com.example.realestateapp.Views.AllAssets;
import com.example.realestateapp.Views.addAsset;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseUser user;
    private ShapeableImageView profileImage_SIV_login;
    private MaterialTextView name_TXT_login;
    private MaterialTextView email_TXT_login;
    private MaterialTextView phone_TXT_login;
    private MaterialButton showMyAssets_BTN_login;
    private MaterialTextView login_TXTBTN_logout;
    private Intent intentFromLogIn;
    private String currentUID;
    private Agent newAgent;
    private String name;
    private String email;
    private String phone;
    private Uri profilePicUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        checkIfUserConnected();
        createRefForNewUser();
        findViews();
        initViews();
    }

    private void createRefForNewUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       if (user != null) {
            if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                DatabaseReference newUserRef = FirebaseDatabase.getInstance().getReference("Agents").child(user.getUid());
                newAgent = new Agent();
                newAgent.setUid(user.getUid());
                newAgent.setAllAssets(new HashMap<>());
                newUserRef.setValue(newAgent);
            }
        }
    }

    private void checkIfUserConnected() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            login();
        }else{
            getCurrentUserInformation();
        }
    }

    private void initViews() {
        login_TXTBTN_logout.setOnClickListener(v -> {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            login();
                        }
                    });
        });
        showMyAssets_BTN_login.setOnClickListener(v->{
            intentFromLogIn = new Intent(LoginActivity.this, AllAssets.class);
            startActivity(intentFromLogIn);
        });
        name_TXT_login.setText(name);
        email_TXT_login.setText(email);
        phone_TXT_login.setText(phone);
        if(profilePicUri==null){
            Glide.with(this).load(R.drawable.defaultprofilepicture).into(profileImage_SIV_login);
        }else {
            Glide.with(this).load(profilePicUri).into(profileImage_SIV_login);
        }
    }

    private void getCurrentUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        currentUID = user.getUid();
        name = user.getDisplayName();
        email = user.getEmail();
        phone = user.getPhoneNumber();
        profilePicUri=user.getPhotoUrl();
    }

    private void findViews() {
        showMyAssets_BTN_login = findViewById(R.id.showMyAssets_BTN_login);
        login_TXTBTN_logout = findViewById(R.id.login_TXTBTN_logout);
        name_TXT_login = findViewById(R.id.name_TXT_login);
        email_TXT_login=findViewById(R.id.email_TXT_login);
        phone_TXT_login=findViewById(R.id.phone_TXT_login);
        profileImage_SIV_login = findViewById(R.id.profileImage_SIV_login);
    }

    private void login() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.building)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            user = FirebaseAuth.getInstance().getCurrentUser();
            getCurrentUserInformation();
            createRefForNewUser();
            initViews();
        }
    }
}