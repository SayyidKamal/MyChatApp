package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karan.churi.PermissionManager.PermissionManager;

import java.security.Permission;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    PermissionManager permissionManager;
    Button login, register, trial;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

        //permissionManager = new PermissionManager() {};
        //permissionManager.checkAndRequestPermissions(this);


        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);
        //trial = findViewById(R.id.btn_Trial);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode,permissions,grantResults);

        ArrayList<String> granted = permissionManager.getStatus().get(0).granted;
        ArrayList<String> denial = permissionManager.getStatus().get(0).denied;
    }

    public void toLogin(View view) {
        startActivity(new Intent(StartActivity.this,LoginActivity.class));
    }

    public void toSignupActivity(View view) {
        startActivity(new Intent(StartActivity.this,Registration.class));
    }
}
