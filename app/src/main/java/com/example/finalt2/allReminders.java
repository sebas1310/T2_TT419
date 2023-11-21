package com.example.finalt2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class allReminders extends AppCompatActivity {


    private ListView allList;


    private ArrayAdapter adapter;


    private static ArrayList<Lembrete> showList;

    private LembreteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reminders);

        db = new LembreteDB(this);

        allList = (ListView) findViewById(R.id.allRemindersXML);
        //chama o método findAll que devolve um array e guarda em exibeLista
        showList = db.findAll();
        //criação de uma instância de um ListAdapter utilizando um layout nativo
        adapter = new ArrayAdapter<Lembrete>(this, android.R.layout.simple_list_item_1, showList);

        //associação a ListView com o adapter
        allList.setAdapter(adapter);


    }
}