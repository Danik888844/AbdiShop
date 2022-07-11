package com.example.lesson03.info;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lesson03.MainActivity;
import com.example.lesson03.ObjectSerializer;
import com.example.lesson03.R;
import com.example.lesson03.entity.Item;
import com.example.lesson03.entity.PenBox;

import java.io.IOException;
import java.util.ArrayList;

public class PenBoxInfoActivity extends AppCompatActivity {
    private TextView tvName;
    private ImageView ivImage;
    private TextView tvBrand;
    private TextView tvColor;
    private TextView tvClaspType;
    private TextView tvMaterial;
    private TextView tvPrice;
    private TextView tvId;
    private Button btnBack;
    private Button btnToCart;

    private ArrayList<Item> itemsToCart = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penbox_info);

        PenBox penBox = (PenBox) getIntent().getExtras().get("penBox");

        tvName = findViewById(R.id.tvName);
        ivImage = findViewById(R.id.ivImage);
        tvBrand = findViewById(R.id.tvBrand);
        tvColor = findViewById(R.id.tvColor);
        tvClaspType = findViewById(R.id.tvClaspType);
        tvMaterial = findViewById(R.id.tvMaterial);
        tvPrice = findViewById(R.id.tvPrice);
        tvId = findViewById(R.id.tvId);

        tvName.setText(penBox.getName());
        ivImage.setImageResource(R.drawable.penbox);
        tvBrand.setText(penBox.getBrand());
        tvColor.setText(penBox.getColor());
        tvClaspType.setText(penBox.getClasp_type());
        tvMaterial.setText(penBox.getMaterial());
        String price = penBox.getPrice() + "₸";
        tvPrice.setText(price);
        tvId.setText(String.valueOf(penBox.getId()));

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
                itemsToCart.add(new Item(penBox.getName(),penBox.getPrice(),R.drawable.penbox, penBox.getId()));
                editor.putString(MainActivity.APP_PREFERENCES_CART, ObjectSerializer.serialize(itemsToCart));
                editor.apply();
                Toast.makeText(v.getContext(),1 + " " + v.getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}