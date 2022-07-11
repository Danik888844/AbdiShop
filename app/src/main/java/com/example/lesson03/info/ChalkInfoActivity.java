package com.example.lesson03.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lesson03.MainActivity;
import com.example.lesson03.ObjectSerializer;
import com.example.lesson03.R;
import com.example.lesson03.entity.Chalk;
import com.example.lesson03.entity.Item;

import java.io.IOException;
import java.util.ArrayList;

public class ChalkInfoActivity extends AppCompatActivity {
    private TextView tvName;
    private ImageView ivImage;
    private TextView tvBrand;
    private TextView tvColor;
    private TextView tvCount;
    private TextView tvPrice;
    private TextView tvId;
    private Button btnBack;
    private Button btnToCart;

    private ArrayList<Item> itemsToCart = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chalk_info);

        Chalk chalk = (Chalk) getIntent().getExtras().get("chalk");

        tvName = findViewById(R.id.tvName);
        ivImage = findViewById(R.id.ivImage);
        tvBrand = findViewById(R.id.tvBrand);
        tvColor = findViewById(R.id.tvColor);
        tvCount = findViewById(R.id.tvCount);
        tvPrice = findViewById(R.id.tvPrice);
        tvId = findViewById(R.id.tvId);

        tvName.setText(chalk.getName());
        ivImage.setImageResource(R.drawable.chalk);
        tvBrand.setText(chalk.getBrand());
        tvColor.setText(chalk.getColor());
        tvCount.setText(String.valueOf(chalk.getCount()));
        String price = chalk.getPrice() + "₸";
        tvPrice.setText(price);
        tvId.setText(String.valueOf(chalk.getId()));

        btnBack = findViewById(R.id.btnBack);
        btnToCart = findViewById(R.id.btnToCart);

        btnBack.setOnClickListener((v)->{
            finish();
        });

        btnToCart.setOnClickListener((v)->{
            sharedPreferences = v.getContext().getSharedPreferences("my_settings", v.getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            try {
                itemsToCart = (ArrayList<Item>) ObjectSerializer.deserialize(sharedPreferences.getString(MainActivity.APP_PREFERENCES_CART, ""));
                itemsToCart.add(new Item(chalk.getName(),chalk.getPrice(),R.drawable.chalk, chalk.getId()));
                editor.putString(MainActivity.APP_PREFERENCES_CART, ObjectSerializer.serialize(itemsToCart));
                editor.apply();
                Toast.makeText(v.getContext(),1 + " " + v.getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}