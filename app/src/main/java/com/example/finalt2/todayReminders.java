package com.example.finalt2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class todayReminders extends AppCompatActivity {

    private ListView allList;


    private ArrayAdapter adapter;


    private static ArrayList<Lembrete> todayList;

    private LembreteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_reminders);


        db = new LembreteDB(this);

        allList = (ListView) findViewById(R.id.todayRemindersXML);
        //chama o método findAll que devolve um array e guarda em exibeLista
        todayList = db.findTodayReminders();
        //criação de uma instância de um ListAdapter utilizando um layout nativo
        adapter = new ArrayAdapter<Lembrete>(this, android.R.layout.simple_list_item_1, todayList);

        //associação a ListView com o adapter
        allList.setAdapter(adapter);
    }
}