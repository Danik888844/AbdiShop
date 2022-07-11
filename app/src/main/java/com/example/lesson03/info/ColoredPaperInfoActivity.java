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
import com.example.lesson03.entity.Chalk;
import com.example.lesson03.entity.ColoredPaper;
import com.example.lesson03.entity.Item;

import java.io.IOException;
import java.util.ArrayList;

public class ColoredPaperInfoActivity extends AppCompatActivity {
    private TextView tvName;
    private ImageView ivImage;
    private TextView tvBrand;
    private TextView tvType;
    private TextView tvFormat;
    private TextView tvColorCount;
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
        setContentView(R.layout.colored_paper_info);

        ColoredPaper coloredPaper = (ColoredPaper) getIntent().getExtras().get("coloredPaper");

        tvName = findViewById(R.id.tvName);
        ivImage = findViewById(R.id.ivImage);
        tvBrand = findViewById(R.id.tvBrand);
        tvType = findViewById(R.id.tvType);
        tvFormat = findViewById(R.id.tvFormat);
        tvColorCount = findViewById(R.id.tvColorCount);
        tvPapersCount = findViewById(R.id.tvPapersCount);
        tvPrice = findViewById(R.id.tvPrice);
        tvId = findViewById(R.id.tvId);

        tvName.setText(coloredPaper.getName());
        ivImage.setImageResource(R.drawable.colored_paper);
        tvBrand.setText(coloredPaper.getBrand());
        tvType.setText(coloredPaper.getType());
        tvFormat.setText(coloredPaper.getFormat());
        tvColorCount.setText(String.valueOf(coloredPaper.getColor_count()));
        tvPapersCount.setText(String.valueOf(coloredPaper.getPapers_count()));
        String price = coloredPaper.getPrice() + "₸";
        tvPrice.setText(price);
        tvId.setText(String.valueOf(coloredPaper.getId()));

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
                itemsToCart.add(new Item(coloredPaper.getName(),coloredPaper.getPrice(),R.drawable.colored_paper, coloredPaper.getId()));
                editor.putString(MainActivity.APP_PREFERENCES_CART, ObjectSerializer.serialize(itemsToCart));
                editor.apply();
                Toast.makeText(v.getContext(),1 + " " + v.getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}