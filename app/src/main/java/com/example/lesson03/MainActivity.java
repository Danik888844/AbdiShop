package com.example.lesson03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lesson03.activities.AllProductsActivity;
import com.example.lesson03.entity.Cart;
import com.example.lesson03.entity.Item;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES_NAME = "User_Name";
    public static final String APP_PREFERENCES_SURNAME = "User_Surname";
    public static final String APP_PREFERENCES_LOGIN = "User_Login";
    public static final String APP_PREFERENCES_PASSWORD = "User_Password";
    public static final String APP_PREFERENCES_IMAGE = "User_Image";
    public static final String APP_PREFERENCES_RED = "User_Red";
    public static final String APP_PREFERENCES_GREEN = "User_Green";
    public static final String APP_PREFERENCES_BLUE = "User_Blue";
    public static final String APP_PREFERENCES_CART = "User_Cart";
    private SharedPreferences sharedPreferences;
    public String userName;
    public String userSurname;
    public String userLogin;
    public String userPassword;
    private Cart cart = new Cart();
    private ArrayList<Item> items = new ArrayList<>();

    private Button btnRegister;
    private EditText etName;
    private EditText etSurname;
    private EditText etLogin;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("my_settings", MODE_PRIVATE);

        if(sharedPreferences.contains(APP_PREFERENCES_LOGIN)){
            toAllProducts();
        }


        initView();
        setListeners();
    }

    private void initView(){
        btnRegister = findViewById(R.id.btnRegister);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
    }

    public void setListeners() {
        btnRegister.setOnClickListener(this::saveUser);
    }

    private void saveUser(View view) {
        userName = etName.getText().toString().trim();
        userSurname = etSurname.getText().toString().trim();
        userLogin = etLogin.getText().toString().trim();
        userPassword = etPassword.getText().toString().trim();

        if (userName.matches("") || userSurname.matches("")|| userLogin.matches("")|| userPassword.matches("")) {
            Toast.makeText(this, R.string.dontmatch, Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putString(APP_PREFERENCES_NAME, userName);
            editor.putString(APP_PREFERENCES_SURNAME, userSurname);
            editor.putString(APP_PREFERENCES_LOGIN, userLogin);
            editor.putString(APP_PREFERENCES_PASSWORD, userPassword);
            editor.putString(APP_PREFERENCES_IMAGE, "");
            editor.putString(APP_PREFERENCES_RED, String.valueOf(cart.getRed()));
            editor.putString(APP_PREFERENCES_GREEN, String.valueOf(cart.getGreen()));
            editor.putString(APP_PREFERENCES_BLUE, String.valueOf(cart.getBlue()));
            editor.putString(APP_PREFERENCES_CART, ObjectSerializer.serialize(items));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();

        toAllProducts();
    }

    private void toAllProducts(){
        Intent intent = new Intent(this, AllProductsActivity.class);
        startActivity(intent);
        finish();
    }

}