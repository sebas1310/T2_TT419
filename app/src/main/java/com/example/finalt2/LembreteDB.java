package com.example.finalt2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LembreteDB extends SQLiteOpenHelper {
    public static final String TAG = "sql";
    public static final String NOME_BANCO = "mybd.db";
    public static final int VERSAO_BANCO = 1;
    public static final String TABLE_NAME = "lembretes";
    public static final String COLUNA0 = "_id";
    public static final String COLUNA1 = "title";
    public static final String COLUNA2 = "description";
    public static final String COLUNA3 = "date";
    public static final String COLUNA4 = "valid";



    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " +TABLE_NAME + " ("
                    + COLUNA0 +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUNA1 + " TEXT,"
                    + COLUNA2 + " TEXT,"
                    + COLUNA3 + " TEXT,"
                    + COLUNA4 + " INT )";




    public LembreteDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 4 - criação da tabela de contatos
        db.execSQL(SQL_CREATE_TABLE);

        //Logcat para informar a criação da tabela
        Log.d(TAG, "Tabela"+TABLE_NAME+" criada com sucesso");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long saveReminder(Lembrete lembrete){
        //lê o valor do id do objeto ( Se id = 0 cadastro/ Se id!=0 atualização)
        long id = lembrete.get_id();
        //abre a conexão com o banco de dados
        SQLiteDatabase db = getWritableDatabase();//abre a conexão com o banco de dados
        try{

            ContentValues valores = new ContentValues();

            Log.d("hola sebas", "titulo: " + lembrete.getTitle());

            valores.put(COLUNA1,lembrete.getTitle());
            valores.put(COLUNA2, lembrete.getDescription());
            valores.put(COLUNA3, lembrete.getDate());
            valores.put(COLUNA4, lembrete.getValid());
            if(id!=0){//se já existe este contato e queremos simplesmente atualizá-lo

                int count = db.update(TABLE_NAME, valores, "_id =?",new String[]{String.valueOf(id)});
                return count; // retorna o numero de linhas alteradas.

            }
            else{//se não existe o contato e vamos incluí-lo na tabela.
                id = db.insert(TABLE_NAME,null,valores);
                return id; //retorna o ID da nova linha inserida ou -1 se ocorrer erro

            }
        }finally{
            db.close();//fecha a conexão
        }
    }



    public ArrayList<Lembrete> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        //Declarando o ArrayList de objetos do tipo Contato.
        ArrayList<Lembrete> lista = new ArrayList<>();
        try {

            Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {//move o cursor para o primeiro registro

                do {
                    int id = c.getInt(c.getColumnIndexOrThrow("_id"));
                    String title = c.getString(c.getColumnIndexOrThrow("title"));
                    String description = c.getString(c.getColumnIndexOrThrow("description"));
                    String date = c.getString(c.getColumnIndexOrThrow("date"));
                    int valid = c.getInt(c.getColumnIndexOrThrow("valid"));

                    Lembrete currentLembrete = new Lembrete(id, title, description,date,valid);
                    lista.add(currentLembrete);

                } while (c.moveToNext());//move para a próxima posição

            }
            return lista;//retorna o Array contendo os contatos
        } finally {
            db.close();//fecha a conexão
        }
    }


    public ArrayList<Lembrete> findTodayReminders() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Lembrete> lista = new ArrayList<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String todayDate = dateFormat.format(new Date()); // Obtiene la fecha actual en el formato "DD/MM/AAAA"

            // Condiciones de la consulta para seleccionar lembretes con la fecha de hoy
            String selection = "date = ?";
            String[] selectionArgs = {todayDate};

            Cursor c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndexOrThrow("_id"));
                    String title = c.getString(c.getColumnIndexOrThrow("title"));
                    String description = c.getString(c.getColumnIndexOrThrow("description"));
                    String date = c.getString(c.getColumnIndexOrThrow("date"));
                    int valid = c.getInt(c.getColumnIndexOrThrow("valid"));

                    Lembrete currentLembrete = new Lembrete(id, title, description, date, valid);
                    lista.add(currentLembrete);
                } while (c.moveToNext());
            }
            return lista;
        } finally {
            db.close();
        }
    }



    public int apagarLembretes() {
        SQLiteDatabase db = getWritableDatabase(); // Abre la conexión con la base de datos
        try {
            int count = db.delete(TABLE_NAME, null, null); // Elimina todos los registros
            Log.i(TAG, "Todos los registros eliminados =>" + count);
            return count; // Retorna el número de registros eliminados
        } finally {
            db.close(); // Cierra la conexión
        }
    }



}
