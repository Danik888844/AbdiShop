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
import com.example.lesson03.entity.Diary;
import com.example.lesson03.entity.Item;

import java.io.IOException;
import java.util.ArrayList;

public class DiaryInfoActivity extends AppCompatActivity {
    private TextView tvName;
    private ImageView ivImage;
    private TextView tvBrand;
    private TextView tvType;
    private TextView tvFormat;
    private TextView tvPapersCount;
    private TextView tvPrice;
    private TextView tvId;
    private Button btnBack;
    private Button btnToCart;

    private ArrayList<Item> itemsToCart = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_info);

        Diary diary = (Diary) getIntent().getExtras().get("diary");

        tvName = findViewById(R.id.tvName);
        ivImage = findViewById(R.id.ivImage);
        tvBrand = findViewById(R.id.tvBrand);
        tvType = findViewById(R.id.tvType);
        tvFormat = findViewById(R.id.tvFormat);
        tvPapersCount = findViewById(R.id.tvPapersCount);
        tvPrice = findViewById(R.id.tvPrice);
        tvId = findViewById(R.id.tvId);

        tvName.setText(diary.getName());
        ivImage.setImageResource(R.drawable.diary);
        tvBrand.setText(diary.getBrand());
        tvType.setText(diary.getPaper_type());
        tvFormat.setText(diary.getPaper_format());
        tvPapersCount.setText(String.valueOf(diary.getPaper_count()));
        String price = diary.getPrice() + "₸";
        tvPrice.setText(price);
        tvId.setText(String.valueOf(diary.getId()));

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
                itemsToCart.add(new Item(diary.getName(),diary.getPrice(),R.drawable.diary, diary.getId()));
                editor.putString(MainActivity.APP_PREFERENCES_CART, ObjectSerializer.serialize(itemsToCart));
                editor.apply();
                Toast.makeText(v.getContext(),1 + " " + v.getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}