package com.example.facepoint20;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaTeste extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public ListView listTest;
    public Button botao, atualizar;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_teste);
        listTest = (ListView) findViewById(R.id.listTest);
        atualizar = (Button) findViewById(R.id.atualizar);
        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarDados();
            }
        });
        listTest.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                excluir(i);
                return true;
            }
        });
        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirAlterar();
                listarDados();
            }
        });
        listarDados();

    }
    public void listarDados(){
        arrayIds = new ArrayList<>();
        try{
            bancoDados = openOrCreateDatabase("bd_fc",MODE_PRIVATE,null);
            Cursor meu_cursor = bancoDados.rawQuery("SELECT id, nome FROM usuarios", null);
            ArrayList<String> linhas = new ArrayList<String>();
            // cria uma conecção entre o layout e o codigo
            ArrayAdapter meu_adapiter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listTest.setAdapter(meu_adapiter);
            meu_cursor.moveToFirst();
            while(meu_cursor!=null){
                linhas.add(meu_cursor.getString(1));
                arrayIds.add(meu_cursor.getInt(0));
                meu_cursor.moveToNext();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void inserirDados(){
        try {
            bancoDados = openOrCreateDatabase("bd_fc", MODE_PRIVATE, null);
            String sql = "INSERT INTO usuarios(nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1, "pele");
            stmt.executeInsert();

            bancoDados.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void abrirAlterar(){
        Intent intent = new Intent(this,ALTERAR.class);
        intent.putExtra("id",idSelecionado);
        startActivity(intent);
    }
    public void excluir(Integer i ) {
        //Toast.makeText(this,i.toString(),Toast.LENGTH_SHORT).show();
        try{
            bancoDados = openOrCreateDatabase("bd_fc", MODE_PRIVATE, null);
            String sql = "DELETE FROM usuarios WHERE id = ?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1,arrayIds.get(i));
            stmt.executeUpdateDelete();
            listarDados();
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}