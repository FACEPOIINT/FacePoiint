package com.example.facepoint20;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void abririJanelaConfirmar(View v) {
        Intent confirmar = new Intent(this, CONFIRMAR.class);
        startActivity(confirmar);
    }
    public void AbrirOutraJanela(View v){
        Intent TelaRegistrar = new Intent(this,TELA_REGISTRAR.class);
        startActivity(TelaRegistrar);
    }
}