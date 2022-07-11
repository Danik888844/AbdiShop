package com.example.lesson03.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson03.MainActivity;
import com.example.lesson03.NetworkService;
import com.example.lesson03.ObjectSerializer;
import com.example.lesson03.PlaceHolderApi;
import com.example.lesson03.R;
import com.example.lesson03.adapters.CartAdapter;
import com.example.lesson03.entity.Item;
import com.example.lesson03.entity.Order;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private ArrayList<Item> items;
    private CartAdapter cartAdapter;
    private String image;
    private String name;
    private int cost = 0;
    private String red;
    private String green;
    private String blue;
    private String stringCost;
    private TextView tvCost;

    private SharedPreferences sharedPreferences;
    private PlaceHolderApi placeHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        placeHolderApi = NetworkService.getInstance().getPlaceHolderApi();

        sharedPreferences = getSharedPreferences("my_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        tvCost = findViewById(R.id.tvCost);
        ImageView ivAvatar = findViewById(R.id.ivAvatar);
        TextView tvAvatar = findViewById(R.id.tvAvatar);
        Button btnBuy = findViewById(R.id.btnBuy);
        Button btnRefresh = findViewById(R.id.btnRefresh);
        RecyclerView rvProducts = findViewById(R.id.rvProducts);

        if(!sharedPreferences.contains(MainActivity.APP_PREFERENCES_LOGIN)) {
            return;
        }

        initPref();

        for(Item i : items){
            cost += i.getCount() * i.getPrice();
        }

        stringCost = cost + "₸";

        tvCost.setText(stringCost);

        if(!image.equals("")) {
            ivAvatar.setVisibility(View.VISIBLE);
            ivAvatar.setImageBitmap(StringToBitMap(image));
        }else {
            tvAvatar.setVisibility(View.VISIBLE);
            tvAvatar.setText(name.substring(0, 1));
            Drawable background = tvAvatar.getBackground();
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(Color.argb(160, Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue)));
        }

        cartAdapter = new CartAdapter(items);
        rvProducts.setAdapter(cartAdapter);

        btnRefresh.setOnClickListener((v)->{

            initPref();

            cost = 0;

            for(Item i : items){
                cost += i.getCount() * i.getPrice();
            }

            cartAdapter.notifyDataSetChanged();
            stringCost = cost + "₸";
            tvCost.setText(stringCost);
        });

        btnBuy.setOnClickListener((v)->{
            putOrder(editor);
        });


    }

    private void initPref(){
        if (items == null) {
            items = new ArrayList<Item>();
        }

        try {
            items = (ArrayList<Item>) ObjectSerializer.deserialize(sharedPreferences.getString(MainActivity.APP_PREFERENCES_CART, ""));
            image = sharedPreferences.getString(MainActivity.APP_PREFERENCES_IMAGE, "");
            name = sharedPreferences.getString(MainActivity.APP_PREFERENCES_NAME, "");
            red = sharedPreferences.getString(MainActivity.APP_PREFERENCES_RED, "");
            green = sharedPreferences.getString(MainActivity.APP_PREFERENCES_GREEN, "");
            blue = sharedPreferences.getString(MainActivity.APP_PREFERENCES_BLUE, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_logout:
                logOut();
                return true;
            case R.id.action_profile:
                toProfile();
                return true;
            case R.id.action_categories:
                productCategories();
                return true;
            case R.id.action_products:
                toAllProducts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //------------------------------------------------------------------- MY METHODS

    public void logOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void productCategories(){
        Intent intent = new Intent(this, ProductCategories.class);
        startActivity(intent);
        finish();
    }

    public void toAllProducts(){
        Intent intent = new Intent(this, AllProductsActivity.class);
        startActivity(intent);
        finish();
    }

    public void toProfile(){
        if(sharedPreferences.contains(MainActivity.APP_PREFERENCES_LOGIN)){
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void putOrder(SharedPreferences.Editor editor) {
        String stringItems;

        try {
            stringItems = ObjectSerializer.serialize(items);
        }
        catch (Exception e){
            stringItems = e.getMessage();
        }

        Order order = new Order();
        order.setContent(stringItems);
        order.setPrice(cost);

        Call<Order> callOrder = placeHolderApi.postOrder(order);
        callOrder.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {

                Toast.makeText(CartActivity.this, R.string.bought, Toast.LENGTH_SHORT).show();

                try {
                    items.clear();
                    editor.putString(MainActivity.APP_PREFERENCES_CART, ObjectSerializer.serialize(items));
                    editor.apply();
                    cartAdapter.notifyDataSetChanged();
                    tvCost.setText("0₸");
                }
                catch (Exception e){
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}

