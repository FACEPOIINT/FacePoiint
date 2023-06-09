package com.example.facepoint20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class TELACADASTARA extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public ListView listTest;
    public EditText cadastroNome, idusuario ,departamento ;
    public Button botaoCadastro;
    String[] mensagens = {"preencha todos os campos", "Cadastro realizado com sucesso"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telacadastara);

        listTest = (ListView)findViewById(R.id.listTest);
        botaoCadastro = (Button)findViewById(R.id.botaoAlterar);
        cadastroNome = (EditText)findViewById(R.id.alterarNome);
        idusuario = (EditText)findViewById(R.id.idusuario);
        departamento = (EditText)findViewById(R.id.departamento);

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastrarFire(v);
            }
        });
    }
    public void cadastrarFire(View v){
        String email = cadastroNome.getText().toString();
        String senha = idusuario.getText().toString();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Snackbar snackbar = Snackbar.make(v,mensagens[1],Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }
    public void criarBanco(){
        try {
            bancoDados = openOrCreateDatabase("bd_fc",MODE_PRIVATE,null);
            //criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS usuarios(" + " id INTEGER PRIMARY KEY AUTOINCREMENT " + ", nome VARCHAR )");
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarDados(){
        try{
            bancoDados = openOrCreateDatabase("bd_fc",MODE_PRIVATE,null);
            Cursor meu_cursor = bancoDados.rawQuery("SELECT id,nome FROM usuarios", null);
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
                meu_cursor.moveToNext();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void abrirJanela() {

        if (!TextUtils.isEmpty(cadastroNome.getText().toString())) {
            Intent cadastro = new Intent(this, TelaTeste.class);
            startActivity(cadastro);
        }
    }
    public void cadastrar() {
        if (!TextUtils.isEmpty(cadastroNome.getText().toString())) {
            try {
                bancoDados = openOrCreateDatabase("bd_fc", MODE_PRIVATE, null);
                String sql = "INSERT INTO usuarios (nome) VALUES (?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);

                stmt.bindString(1, cadastroNome.getText().toString());
                stmt.executeInsert();
                Toast.makeText(this,"CADASTRADO COM SUCESSO",Toast.LENGTH_LONG).show();
                bancoDados.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this,"DIGITE EM TODOS OS CAMPOS ",Toast.LENGTH_LONG).show();
        }
    }
}