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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson03.MainActivity;
import com.example.lesson03.NetworkService;
import com.example.lesson03.PlaceHolderApi;
import com.example.lesson03.R;
import com.example.lesson03.adapters.ItemAdapter;
import com.example.lesson03.entity.Chalk;
import com.example.lesson03.entity.ColoredPaper;
import com.example.lesson03.entity.Diary;
import com.example.lesson03.entity.Item;
import com.example.lesson03.entity.PenBox;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategories extends AppCompatActivity {
    private RecyclerView rvProducts;
    private ItemAdapter itemAdapter;

    private Button btnChalk;
    private Button btnColoredPaper;
    private Button btnDiary;
    private Button btnPenBox;

    private ImageView ivAvatar;
    private TextView tvAvatar;
    private SharedPreferences sharedPreferences;

    private List<Chalk> chalks;
    private Call<List<Chalk>> callChalk;

    private List<ColoredPaper> coloredPapers;
    private Call<List<ColoredPaper>> callColoredPaper;

    private List<Diary> diaries;
    private Call<List<Diary>> callDiary;

    private List<PenBox> penBoxes;
    private Call<List<PenBox>> callPenBox;

    private ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certain_product);

        sharedPreferences = getSharedPreferences("my_settings", MODE_PRIVATE);
        PlaceHolderApi placeHolderApi = NetworkService.getInstance().getPlaceHolderApi();

        rvProducts = findViewById(R.id.rvProducts);
        btnChalk = findViewById(R.id.btnChalk);
        btnColoredPaper = findViewById(R.id.btnColoredPaper);
        btnDiary = findViewById(R.id.btnDiary);
        btnPenBox = findViewById(R.id.btnPenBox);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvAvatar = findViewById(R.id.tvAvatar);

        callChalk = placeHolderApi.getAllChalks();
        callColoredPaper = placeHolderApi.getAllColoredPapers();
        callDiary = placeHolderApi.getAllDiaries();
        callPenBox = placeHolderApi.getAllPenBoxes();

        String image = sharedPreferences.getString(MainActivity.APP_PREFERENCES_IMAGE, "");
        String name = sharedPreferences.getString(MainActivity.APP_PREFERENCES_NAME, "");
        String red = sharedPreferences.getString(MainActivity.APP_PREFERENCES_RED, "");
        String green = sharedPreferences.getString(MainActivity.APP_PREFERENCES_GREEN, "");
        String blue = sharedPreferences.getString(MainActivity.APP_PREFERENCES_BLUE, "");

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

        btnChalk.setOnClickListener(view->{
            rvProducts.setVisibility(View.VISIBLE);
            items.clear();

            setChalks();
        });

        btnColoredPaper.setOnClickListener(view->{
            rvProducts.setVisibility(View.VISIBLE);
            items.clear();

            setColoredPapers();
        });

        btnDiary.setOnClickListener(view->{
            rvProducts.setVisibility(View.VISIBLE);
            items.clear();

            setDiaries();
        });

        btnPenBox.setOnClickListener(view->{
            rvProducts.setVisibility(View.VISIBLE);
            items.clear();

            setPenBoxes();
        });

    }

    public void setChalks(){

        callChalk.clone().enqueue(new Callback<List<Chalk>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chalk>> call, @NonNull Response<List<Chalk>> response) {
                chalks = response.body();
                for (Chalk c : chalks) {
                    items.add(new Item(c.getName(),c.getPrice(),R.drawable.chalk, c.getId()));
                }

                itemAdapter = new ItemAdapter(items);
                rvProducts.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Chalk>> call, @NonNull Throwable t) {
                Log.d("Log",  t.toString());
            }
        });
    }

    public void setColoredPapers(){

        callColoredPaper.clone().enqueue(new Callback<List<ColoredPaper>>() {
            @Override
            public void onResponse(@NonNull Call<List<ColoredPaper>> call, @NonNull Response<List<ColoredPaper>> response) {
                coloredPapers = response.body();
                for (ColoredPaper c : coloredPapers) {
                    items.add(new Item(c.getName(),c.getPrice(),R.drawable.colored_paper,c.getId()));
                }

                itemAdapter = new ItemAdapter(items);
                rvProducts.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<ColoredPaper>> call, @NonNull Throwable t) {
                Log.d("Log",  t.toString());
            }
        });
    }

    public void setDiaries() {

        callDiary.clone().enqueue(new Callback<List<Diary>>() {
            @Override
            public void onResponse(@NonNull Call<List<Diary>> call, @NonNull Response<List<Diary>> response) {
                diaries = response.body();
                for (Diary d : diaries) {
                    items.add(new Item(d.getName(), d.getPrice(), R.drawable.diary, d.getId()));
                }

                itemAdapter = new ItemAdapter(items);
                rvProducts.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Diary>> call, @NonNull Throwable t) {
                Log.d("Log", t.toString());
            }
        });
    }

    public void setPenBoxes(){
        callPenBox.clone().enqueue(new Callback<List<PenBox>>() {
            @Override
            public void onResponse(@NonNull Call<List<PenBox>> call, @NonNull Response<List<PenBox>> response) {
                penBoxes = response.body();
                for (PenBox pb : penBoxes) {
                    items.add(new Item(pb.getName(),pb.getPrice(),R.drawable.penbox, pb.getId()));
                }

                itemAdapter = new ItemAdapter(items);
                rvProducts.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<PenBox>> call, @NonNull Throwable t) {
                Log.d("Log",  t.toString());
            }
        });

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
                runAnimation();
                return true;
            case R.id.action_products:
                allProducts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //------------------------------------------------------------------- MY METHODS

    private void runAnimation() {

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);

        if(rvProducts.getAdapter() != null){
            rvProducts.setLayoutAnimation(controller);
            rvProducts.getAdapter().notifyDataSetChanged();
            rvProducts.scheduleLayoutAnimation();
        }

    }

    public void logOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void allProducts(){
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

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
