package com.itproger.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itproger.foodorder.Models.Category;
import com.itproger.foodorder.Models.Food;

public class FoodDetail extends AppCompatActivity {

    public static int ID = 0;
    private ImageView mainPhoto;
    private TextView foodMainName,price,foodFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mainPhoto = findViewById(R.id.mainPhoto);
        foodFullName = findViewById(R.id.foodFullName);
        foodMainName = findViewById(R.id.foodMainName);
        price = findViewById(R.id.price);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table = database.getReference("Category");


        table.child(String.valueOf(ID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Category category = snapshot.getValue(Category.class);
                foodMainName.setText(category.getName());
                int id = getApplicationContext().getResources().getIdentifier("drawable/" +category.getImage(), null, getApplicationContext().getPackageName());
                mainPhoto.setImageResource(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FoodDetail.this, "Нет интернет соединения", Toast.LENGTH_SHORT).show();
            }
        });
        

        final DatabaseReference table_food = database.getReference("Food");
        table_food.child(String.valueOf(ID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Food foodItem = snapshot.getValue(Food.class);
               price.setText(foodItem.getPrice() + " AZN");
               foodFullName.setText(foodItem.getFull_text());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FoodDetail.this, "Нет интернет соединения", Toast.LENGTH_SHORT).show();
            }
        });


    }
}