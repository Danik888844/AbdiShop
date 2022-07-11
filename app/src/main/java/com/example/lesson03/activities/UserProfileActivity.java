package com.example.lesson03.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lesson03.MainActivity;
import com.example.lesson03.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserProfileActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 100;
    public TextView tvName;
    public TextView tvSurname;
    public ImageView ivAvatar;
    public TextView tvAvatar;
    public Button btnBack;
    public Button btnToCart;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        sharedPreferences = getSharedPreferences("my_settings", MODE_PRIVATE);

        tvName = findViewById(R.id.tvFirstName);
        tvSurname = findViewById(R.id.tvLastName);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvAvatar = findViewById(R.id.tvAvatar);
        btnBack = findViewById(R.id.btnBack);
        btnToCart = findViewById(R.id.btnToCart);

        if(!sharedPreferences.contains(MainActivity.APP_PREFERENCES_LOGIN)) {
            return;
        }

        String image = sharedPreferences.getString(MainActivity.APP_PREFERENCES_IMAGE, "");
        String name = sharedPreferences.getString(MainActivity.APP_PREFERENCES_NAME, "");
        String surname = sharedPreferences.getString(MainActivity.APP_PREFERENCES_SURNAME, "");
        String red = sharedPreferences.getString(MainActivity.APP_PREFERENCES_RED, "");
        String green = sharedPreferences.getString(MainActivity.APP_PREFERENCES_GREEN, "");
        String blue = sharedPreferences.getString(MainActivity.APP_PREFERENCES_BLUE, "");

        tvName.setText(name);
        tvSurname.setText(surname);

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

        btnBack.setOnClickListener((v)->{
            toAllProducts();
        });

        btnToCart.setOnClickListener((v)->{
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
            finish();
        });

        ivAvatar.setOnClickListener((v)->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        });

        tvAvatar.setOnClickListener((v)->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
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
            case R.id.action_categories:
                productCategories();
                return true;
            case R.id.action_products:
                toAllProducts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                ivAvatar.setImageBitmap(selectedImage);
                ivAvatar.setVisibility(View.VISIBLE);
                tvAvatar.setVisibility(View.INVISIBLE);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String encoded = Base64.encodeToString(b, Base64.DEFAULT);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MainActivity.APP_PREFERENCES_IMAGE, encoded);
                editor.apply();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(UserProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(UserProfileActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
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
