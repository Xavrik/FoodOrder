package com.itproger.foodorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itproger.foodorder.Models.Category;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Category> {

    private LayoutInflater layoutInflater;
    private  List<Category> categories;
    private int layoutListRow;
    private Context context;

    public FoodListAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        categories = objects;
        layoutListRow = resource;
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_food = database.getReference("Food");
    final DatabaseReference table = database.getReference("Category");

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArrayList<String> allKeys = new ArrayList<>();
        //allKeys.add(obj.getKey());
        allKeys.add()

        convertView = layoutInflater.inflate(layoutListRow,null);
        Category category= categories.get(position);
        if(category != null){
            TextView foodName = convertView.findViewById(R.id.foodMainName);
            ImageView photo = convertView.findViewById(R.id.mainPhoto);

            if( foodName != null){
                foodName.setText(category.getName());
            }
            if(photo != null){
                int id = getContext().getResources().getIdentifier("drawable/" +category.getImage(), null, getContext().getPackageName());
                photo.setImageResource(id);

                photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FoodDetail.ID = Integer.parseInt(allKeys.get(position));
                        //FoodDetail.ID = position+1;
                        context.startActivity(new Intent(context, FoodDetail.class));

                        //Toast.makeText(getContext(),foodName.getText().toString(),Toast.LENGTH_LONG).show();
                    }
                });

                photo.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        table_food.child(String.valueOf(FoodDetail.ID)).removeValue();
                        table.child(String.valueOf(FoodDetail.ID)).removeValue();
                        return false;
                    }
                });

            }
        }
        return convertView;
    }
}
