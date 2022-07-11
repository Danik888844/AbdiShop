package com.example.lesson03.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson03.BuildConfig;
import com.example.lesson03.MainActivity;
import com.example.lesson03.NetworkService;
import com.example.lesson03.ObjectSerializer;
import com.example.lesson03.PlaceHolderApi;
import com.example.lesson03.R;
import com.example.lesson03.entity.Chalk;
import com.example.lesson03.entity.ColoredPaper;
import com.example.lesson03.entity.Diary;
import com.example.lesson03.entity.Item;
import com.example.lesson03.entity.PenBox;
import com.example.lesson03.info.ChalkInfoActivity;
import com.example.lesson03.info.ColoredPaperInfoActivity;
import com.example.lesson03.info.DiaryInfoActivity;
import com.example.lesson03.info.PenBoxInfoActivity;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Item> items;
    private ArrayList<Item> itemsToCart = new ArrayList<>();

    private PlaceHolderApi placeHolderApi;
    private Chalk chalk;
    private Call<Chalk> callChalk;

    private ColoredPaper coloredPaper;
    private Call<ColoredPaper> callColoredPaper;

    private Diary diary;
    private Call<Diary> callDiary;

    private PenBox penBox;
    private Call<PenBox> callPenBox;

    private SharedPreferences sharedPreferences;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder myViewHolder, int position) {

        Item item = items.get(position);
        myViewHolder.tvName.setText(item.getName());
        String price = String.valueOf(item.getPrice()) + "₸";
        myViewHolder.tvPrice.setText(price);
        myViewHolder.ivImage.setImageResource(item.getImage());
        placeHolderApi = NetworkService.getInstance().getPlaceHolderApi();

        myViewHolder.ivImage.setOnClickListener((v) -> {

            if(item.getImage() == R.drawable.chalk){ // ПОИСК С ПОМОЩЬЮ КАРТИНКИ
                callChalk = placeHolderApi.getChalkById(item.getIdForFind());
                getMyChalk(v.getContext());
            }

            if(item.getImage() == R.drawable.colored_paper){
                callColoredPaper = placeHolderApi.getColoredPaperById(item.getIdForFind());
                getMyColoredPaper(v.getContext());
            }

            if(item.getImage() == R.drawable.diary){
                callDiary = placeHolderApi.getDiaryById(item.getIdForFind());
                getMyDiary(v.getContext());
            }

            if(item.getImage() == R.drawable.penbox){
                callPenBox = placeHolderApi.getPenBoxById(item.getIdForFind());
                getMyPenBox(v.getContext());
            }

        });

        myViewHolder.imgBtnPlus.setOnClickListener((v)->{
            if(item.getCount() < 100){
                item.setCount(item.getCount() + 1);
                myViewHolder.tvCount.setText(String.valueOf(item.getCount()));
            }
            else{
                Toast.makeText(v.getContext(), R.string.maxOneHungred, Toast.LENGTH_SHORT).show();
            }

        });

        myViewHolder.imgBtnMinus.setOnClickListener((v)->{
            if(item.getCount() > 1){
                item.setCount(item.getCount() - 1);
                myViewHolder.tvCount.setText(String.valueOf(item.getCount()));
            }
            else{
                Toast.makeText(v.getContext(),R.string.minOne, Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.btnToCart.setOnClickListener((v)->{
            sharedPreferences = v.getContext().getSharedPreferences("my_settings", v.getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            try {
                itemsToCart = (ArrayList<Item>) ObjectSerializer.deserialize(sharedPreferences.getString(MainActivity.APP_PREFERENCES_CART, ""));
                itemsToCart.add(item);
                editor.putString(MainActivity.APP_PREFERENCES_CART, ObjectSerializer.serialize(itemsToCart));
                editor.apply();
                Toast.makeText(v.getContext(),item.getCount() + " " + v.getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivImage;
        TextView tvPrice;
        TextView tvCount;
        ImageButton imgBtnPlus;
        ImageButton imgBtnMinus;
        Button btnToCart;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCount = itemView.findViewById(R.id.tvCount);
            imgBtnPlus = itemView.findViewById(R.id.imgBtnPlus);
            imgBtnMinus = itemView.findViewById(R.id.imgBtnMinus);
            btnToCart = itemView.findViewById(R.id.btnToCart);
        }
    }


    //------------------------------------------------------------------------- MY METHODS

    private void getMyChalk(Context context){ // ДЛЯ ВЫВОДА ИНФОРМАЦИИ О МЕЛКАХ
        callChalk.enqueue(new Callback<Chalk>() {
            @Override
            public void onResponse(@NonNull Call<Chalk> call, @NonNull Response<Chalk> response) {
                chalk = response.body();// ПОЛУЧАЕМ ОБЪЕКТ

                Intent intent = new Intent(context, ChalkInfoActivity.class); // СОЗДАЕМ "ЦЕЛЬ"
                intent.putExtra("chalk", (Serializable) chalk); // ОТПРАВЛЯЕМ ТУДА НАШ ОБЪЕКТ, НАЙДЕНЫЙ ПО ФОТОГРАФИИ
                context.startActivity(intent); // ЗАПУСКАЕМ АКТИВИТИ С ИНФОРМАЦИЕЙ
            }

            @Override
            public void onFailure(@NonNull Call<Chalk> call, @NonNull Throwable t) {
                Log.d("Log",  t.toString());
            }
        });
    }

    private void getMyColoredPaper(Context context){ // ДЛЯ ВЫВОДА ИНФОРМАЦИИ О ЦВЕТНОЙ БУМАГЕ
        callColoredPaper.enqueue(new Callback<ColoredPaper>() {
            @Override
            public void onResponse(@NonNull Call<ColoredPaper> call, @NonNull Response<ColoredPaper> response) {
                coloredPaper = response.body();

                Intent intent = new Intent(context, ColoredPaperInfoActivity.class);
                intent.putExtra("coloredPaper", (Serializable) coloredPaper);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<ColoredPaper> call, @NonNull Throwable t) {
                Log.d("Log",  t.toString());
            }
        });
    }

    private void getMyDiary(Context context){ // ДЛЯ ВЫВОДА ИНФОРМАЦИИ О ДНЕВНИКЕ
        callDiary.enqueue(new Callback<Diary>() {
            @Override
            public void onResponse(@NonNull Call<Diary> call, @NonNull Response<Diary> response) {
                diary = response.body();

                Intent intent = new Intent(context, DiaryInfoActivity.class);
                intent.putExtra("diary", (Serializable) diary);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<Diary> call, Throwable t) {
                Log.d("Log",  t.toString());
            }
        });
    }

    private void getMyPenBox(Context context){ // ДЛЯ ВЫВОДА ИНФОРМАЦИИ О ПЕНАЛЕ
        callPenBox.enqueue(new Callback<PenBox>() {
            @Override
            public void onResponse(@NonNull Call<PenBox> call, @NonNull Response<PenBox> response) {
                penBox = response.body();

                Intent intent = new Intent(context, PenBoxInfoActivity.class);
                intent.putExtra("penBox", (Serializable) penBox);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<PenBox> call, @NonNull Throwable t) {
                Log.d("Log",  t.toString());
            }
        });
    }
}