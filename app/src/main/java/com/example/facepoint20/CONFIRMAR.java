package com.example.facepoint20;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CONFIRMAR extends AppCompatActivity {
    public EditText ConfirmarUsuario;
    public EditText ConfirmarSenha;
    public Button buttonconf;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);
        ConfirmarUsuario = (EditText) findViewById(R.id.ConfirmarUsuario);
        ConfirmarSenha = (EditText) findViewById(R.id.ConfirmarSenha);
        buttonconf = (Button) findViewById(R.id.buttonconf);
        String usuario = ConfirmarUsuario.getText().toString();
        String senha = ConfirmarSenha.getText().toString();
        buttonconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirmaruser();

            }
        });
    }
    public void Confirmaruser(){
        String usuario = ConfirmarUsuario.getText().toString();
        String senha = ConfirmarSenha.getText().toString();
        if (!TextUtils.isEmpty(ConfirmarUsuario.getText().toString())) {
            if(!TextUtils.isEmpty(ConfirmarSenha.getText().toString())){
                if(usuario.equals("CHEFÃO ") && senha.equals("1234 ")){
                    AbrirJanelaCadastro();
                }else{
                    Toast.makeText(this,"ACESSO NEGADO",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "DIGITE NO CAMPO SENHA!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"DIGITE NO CAMPO USUARIO!",Toast.LENGTH_SHORT).show();
        }
    }
    public void AbrirJanelaCadastro(){

        Intent cadastro = new Intent(this, TELACADASTARA.class);
        startActivity(cadastro);
    }
}