package com.example.mychatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mychatapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.textfield.TextInputEditText;


import java.util.HashMap;

public class Registration extends AppCompatActivity {

    TextInputEditText emailET, pwdET,username;
    //TextView loginLink;
    ProgressDialog pd;

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        emailET = findViewById(R.id.email);
        pwdET = findViewById(R.id.password);
        username = findViewById(R.id.username);

        pd = new ProgressDialog(this);
       // loginLink = findViewById(R.id.textView3);
        auth = FirebaseAuth.getInstance();
        //loginLink.setOnClickListener(this);
    }

    public void registerButton(View view){
        String email = emailET.getText().toString();
        String pwd = pwdET.getText().toString();
        String uname = username.getText().toString();

        //check whether the fields are empty or not
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(pwd) || TextUtils.isEmpty(uname) ){
            Toast.makeText(this,"Please enter data", Toast.LENGTH_SHORT).show();
            return;
        }else if (pwd.length()<6){
            Toast.makeText(this,"Password needs to be at least 6 characters ", Toast.LENGTH_SHORT).show();
        } else {
            register(uname,email,pwd);
        }

    }

    private void register (final String username, String email, String password){
        pd.setMessage("Registering the user....");
        pd.show();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    String userid = firebaseUser.getUid();

                                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                    /*HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put("id",userid);
                                    hashMap.put("username",username);
                                    hashMap.put("image URL","default");*/

                                    User user = new User();
                                    user.setId(userid);
                                    user.setUsername(username);
                                    user.setImageURL("default");
                                    user.setStatus("offline");
                                    user.setStatus(username.toLowerCase());



                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent intent = new Intent(Registration.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });


                                }else{
                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(),
                                            "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }

    public void back(View view) {
        onBackPressed();
    }

    public void toLoginActivity(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }



    /*    @Override
    public void onClick(View view) {
        //go to login page
        if(view == loginLink){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }*/
}
