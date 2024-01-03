package com.example.womenjobfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmailSignUp, editTextPasswordSignUp,name,phone;
    private Button buttonSignUp;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        editTextEmailSignUp = findViewById(R.id.editTextEmailSignUp);
        editTextPasswordSignUp = findViewById(R.id.editTextPasswordSignUp);
        name = findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser() {
        String email = editTextEmailSignUp.getText().toString();
        String password = editTextPasswordSignUp.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign up success, update UI with the signed-in user's information
                    Toast.makeText(SignUpActivity.this, "Sign up successful.",
                            Toast.LENGTH_SHORT).show();

                    Map<String, Object> posts = new HashMap<>();
                    String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    JobModel jobModel = getIntent().getParcelableExtra("job");
                    posts.put("name", name.getText().toString());
                    posts.put("email", email);
                    posts.put("phone", phone.getText().toString());

                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(posts);
                    Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                    startActivity(intent);

                    // Add code to navigate to the next activity or perform other actions
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(SignUpActivity.this, "Sign up failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}