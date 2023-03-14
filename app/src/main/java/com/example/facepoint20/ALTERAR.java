package com.example.facepoint20;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ALTERAR extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public Button botaoAlterar;
    public EditText alterarNome;
    public ListView listTest;
    public Integer id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);
        botaoAlterar = (Button) findViewById(R.id.botaoAlterar);
        alterarNome = (EditText) findViewById(R.id.alterarNome);
        listTest = (ListView) findViewById(R.id.listTest);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        carregarDados();

        botaoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });
    }
    public void carregarDados(){
        try {
            bancoDados = openOrCreateDatabase("bd_fc", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, nome FROM usuarios WHERE id = "+ id.toString(),null);
            cursor.moveToFirst();
            alterarNome.setText(cursor.getString(1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void alterar(){
        String valorNome = alterarNome.getText().toString();
        try {
            bancoDados = openOrCreateDatabase("bd_fc", MODE_PRIVATE, null);
            String sql = "UPDATE usuarios SET nome=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1,valorNome);
            stmt.bindLong(2,id);
            stmt.executeUpdateDelete();
        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
    }
}