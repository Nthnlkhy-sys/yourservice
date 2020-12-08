package com.yuki.yourservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText edtTxtMail, edtTxtPswd;
    private Button btnLgn;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        login();
    }

    public void initView(){
        edtTxtMail      = (EditText) findViewById(R.id.edtTxtMail);
        edtTxtPswd      = (EditText) findViewById(R.id.edtTxtPswd);
        btnLgn          = (Button) findViewById(R.id.btnlogin);
        auth            = FirebaseAuth.getInstance();
    }

    private void login(){
        btnLgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailuser   =   edtTxtMail.getText().toString().trim();
                final String passuser    =   edtTxtPswd.getText().toString().trim();

                if(emailuser.isEmpty()){
                    edtTxtMail.setError("Email Tidak boleh kosong");
                } else if(!Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()){
                    edtTxtMail.setError("Email Tidak Valid");
                } else if(passuser.isEmpty()){
                    edtTxtPswd.setError("Password Harus diisi");
                } else {
                    auth.signInWithEmailAndPassword(emailuser, passuser)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this,
                                                "Failed to Login" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    } else{
                                        Bundle bundle = new Bundle();
                                        bundle.putString("email", emailuser);
                                        bundle.putString("pass", passuser);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("emailpass", bundle));
                                        finish();
                                    }
                                }
                            });
                }

            }
        });
    }
}