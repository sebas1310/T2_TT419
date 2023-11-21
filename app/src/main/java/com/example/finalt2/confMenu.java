package com.example.finalt2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class confMenu extends AppCompatActivity {

    private Button btnApagarLembretes;
    private LembreteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_menu);

        db = new LembreteDB(this);
        btnApagarLembretes = findViewById(R.id.apagarLembretesXML);

        btnApagarLembretes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = db.apagarLembretes();
                if (count == 0) {
                    Toast.makeText(confMenu.this, "Nao tem lembretes!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(confMenu.this, "Lembretes apagados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}