package com.example.finalt2;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;

public class NewReminder extends AppCompatActivity {

    private Button btnSalvar;

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

                if (title.getText().length() == 0 ||description.getText().length() == 0||data.getText().length() == 0) {
                    Toast.makeText(NewReminder.this, "Por favor preencha os campos!", Toast.LENGTH_SHORT).show();

                } else {
                    String titulo = title.getText().toString();
                    String desc = description.getText().toString();
                    String date = data.getText().toString();


                    if (validDate(date)){
                        int valid = 1;
                        Lembrete lembreteCreated = new Lembrete(0, titulo, desc, date,valid);

                        long id = db.saveReminder(lembreteCreated);
                        if (id != -1)
                            Toast.makeText(NewReminder.this, "Lembrete creado!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(NewReminder.this, "Erro.", Toast.LENGTH_LONG).show();

                        title.setText("");
                        description.setText("");
                        data.setText("");
                    }else {
                        Toast.makeText(NewReminder.this, "Erro de data!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean validDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            Calendar currentDate = Calendar.getInstance();
            int currentYear = currentDate.get(Calendar.YEAR);

            Calendar inputDate = Calendar.getInstance();
            inputDate.setTime(sdf.parse(dateStr));

            int inputYear = inputDate.get(Calendar.YEAR);
            int inputMonth = inputDate.get(Calendar.MONTH) + 1;
            int inputDay = inputDate.get(Calendar.DAY_OF_MONTH);

            return inputYear >= currentYear && inputDate.getActualMaximum(Calendar.DAY_OF_MONTH) >= inputDay && inputMonth == inputDate.get(Calendar.MONTH) + 1;
        } catch (ParseException | NullPointerException e) {
            return false;
        }
    }


}