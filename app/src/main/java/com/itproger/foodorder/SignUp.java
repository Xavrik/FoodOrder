package com.itproger.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itproger.foodorder.Models.User;

public class SignUp extends AppCompatActivity {

    private Button btnSignUp;
    private EditText editPhone,editName, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        btnSignUp = findViewById(R.id.btnSignUp);
        editPhone = findViewById(R.id.editTextPhone);
        editName = findViewById(R.id.editTextName);
        editPassword = findViewById(R.id.editTextPassword);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(snapshot.child(editPhone.getText().toString()).exists()){
                           Toast.makeText(SignUp.this, "Такой пользователь уже есть", Toast.LENGTH_LONG).show();
                       }else{
                           User user = new User(editName.getText().toString(),editPassword.getText().toString());
                           table.child(editPhone.getText().toString()).setValue(user);
                           Toast.makeText(SignUp.this, "Успешная регистрация", Toast.LENGTH_LONG).show();
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SignUp.this, "Нет интернет соединения", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}