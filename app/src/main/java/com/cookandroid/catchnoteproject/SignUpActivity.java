package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity
{
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoLoginButton).setOnClickListener(onClickListener);

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.signUpButton:
                    signUp();
                    Log.e("클릭","클릭");
                    break;
                case R.id.gotoLoginButton:
                    myStartActivity(LoginActivity.class);
                    break;
            }
        }
    } ;

    private void signUp() {
        final String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();

        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    writeNewUser(email);
                                    showToast( "회원가입에 성공하였습니다.");
                                } else {
                                    if(task.getException() != null){
                                        showToast("회원가입에 실패하였습니다.");
                                    }
                                }
                            }
                        });

            }else{
                showToast("비밀번호가 일치하지 않습니다.");
            }
        }else {
            showToast( "이메일 또는 비밀번호를 입력해 주세요.");
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void writeNewUser(String userId) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());

        Map<String, Object> userinfo = new HashMap<>();

        User user = new User(userId);

        userinfo = user.toMap();

        userinfo.put("uid", userId);

        mDatabase.setValue(userinfo);

    }
}