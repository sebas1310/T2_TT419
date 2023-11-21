package com.example.finalt2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewReminder extends AppCompatActivity {

    private Button btnSalvar;

    //variáveis correspondente aos campoos que serão preenchido para o cadastro do contato
    private EditText title;
    private EditText description;
    private EditText data;

    private LembreteDB db;

    private int valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        db = new LembreteDB(this);


        btnSalvar = findViewById(R.id.saveXML);
        title = findViewById(R.id.tituloXML);
        description = findViewById(R.id.descripcionXML);
        data = findViewById(R.id.dateXML);



        btnSalvar.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                //verifica se houve tentativa de cadastro sem preenchimento de todos os campos
                if (title.getText().length() == 0 ||description.getText().length() == 0||data.getText().length() == 0) {
                    Toast.makeText(NewReminder.this, "Por favor preencha todos os campos!", Toast.LENGTH_SHORT).show();

                } else {
                    //Convertendo os  conteúdos dos EditText para variáveis do JAVA
                    String titulo = title.getText().toString();
                    String desc = description.getText().toString();
                    String date = data.getText().toString();
                    int valid = 1;
                    Lembrete lembreteCreated = new Lembrete(0, titulo, desc, date,valid);

                    long id = db.saveReminder(lembreteCreated);
                    if (id != -1)
                        Toast.makeText(NewReminder.this, "cadastro realizado!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(NewReminder.this, "Ops! não foi possível cadastrar o contato.", Toast.LENGTH_LONG).show();

                    //limpa as caixa de texto para um novo cadastro.
                    title.setText("");
                    description.setText("");
                    data.setText("");
                }
            }
        });
    }


}