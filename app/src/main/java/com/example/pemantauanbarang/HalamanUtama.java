package com.example.pemantauanbarang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HalamanUtama extends AppCompatActivity implements View.OnClickListener {

    private Button listBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

        listBarang = findViewById(R.id.listBarang);

        listBarang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, listBarang.class);
        startActivity(intent);
    }

}