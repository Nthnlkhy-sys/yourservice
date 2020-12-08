package com.yuki.yourservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsNme, edtTxtMail, edtTxtPswd;

    private Button btnReg;

    private FirebaseAuth auth;

    private DatabaseReference m_database;

    private User User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtTxtMail = (EditText) findViewById(R.id.edtTxtMail);
        edtUsNme = (EditText) findViewById(R.id.edtUsNme);
        edtTxtPswd = (EditText) findViewById(R.id.edtTxtPswd);
        btnReg = (Button) findViewById(R.id.btnreg);
        auth = FirebaseAuth.getInstance();
        m_database = FirebaseDatabase.getInstance().getReference();
        User = new User();

        btnReg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnreg) {
            saveUserdata();
            registerUser();
        }
    }

    private void saveUserdata() {
        String userName = edtUsNme.getText().toString().trim();
        String email = edtTxtMail.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(userName)) {
            isEmptyFields = true;
            edtUsNme.setError("Username tidak boleh Kosong");
        }
        if (!isEmptyFields) {
            Toast.makeText(RegisterActivity.this, "Saving Data....", Toast.LENGTH_SHORT).show();

            DatabaseReference dbUser = m_database.child("User");

            String key = dbUser.push().getKey();
            User.setKey(key);
            User.setUsername(userName);
            User.setEmail(email);
            User.setName("");

            dbUser.child(key).setValue(User);

            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

        }
    }

    private void registerUser() {
        String emailuser = edtTxtMail.getText().toString().trim();
        String pswduser = edtTxtPswd.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()) {
            edtTxtMail.setError("Email tidak Valid");
        } else if (pswduser.isEmpty()) {
            edtTxtPswd.setError("Password tidak boleh kosong");
        } else if (pswduser.length() < 6) {
            edtTxtPswd.setError("Password minimal terdiri dari 6 karakter");
        } else {
            auth.createUserWithEmailAndPassword(emailuser, pswduser)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Register gagal karena " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        }
                    });
        }

    }
}
    /*/*private void initView(){
        edtTxtMail  = (EditText) findViewById(R.id.edtTxtMail);
        edtUsNme    = (EditText) findViewById(R.id.edtUsNme);
        edtTxtPswd  = (EditText) findViewById(R.id.edtTxtPswd);
        btnReg      = (Button) findViewById(R.id.btnreg);
        auth        = FirebaseAuth.getInstance();
        m_database  = FirebaseDatabase.getInstance().getReference();
        User        = new User();
    }



    private void registerUser(){
        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String emailuser    = edtTxtMail.getText().toString().trim();
                String pswduser     = edtTxtPswd.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()) {
                    edtTxtMail.setError("Email tidak Valid");
                } else if (pswduser.isEmpty()) {
                    edtTxtPswd.setError("Password tidak boleh kosong");
                } else if (pswduser.length() < 6) {
                    edtTxtPswd.setError("Password minimal terdiri dari 6 karakter");
                }
                else {
                    auth.createUserWithEmailAndPassword(emailuser, pswduser)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Register gagal karena " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        saveUser();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }
                                }
                            });
                }
            }
        });
    }

    private void saveUser(){
        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userName     = edtUsNme.getText().toString().trim();
                String email        = edtTxtMail.getText().toString().trim();

                boolean isEmptyFields   = false;

                if (TextUtils.isEmpty(userName)){
                    isEmptyFields = true;
                    edtUsNme.setError("Username tidak boleh Kosong");
                } if (!isEmptyFields){
                    Toast.makeText(RegisterActivity.this, "Saving Data....", Toast.LENGTH_SHORT).show();

                    DatabaseReference dbUser    =   m_database.child("User");

                    String key =    dbUser.push().getKey();
                    User.setKey(key);
                    User.setUsername(userName);
                    User.setEmail(email);

                    dbUser.child(key).setValue(User);

                    finish();

                }
            }
        });
    }

}

     */