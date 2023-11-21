package com.example.finalt2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

            //cria a variável que aponta para o objeto ContentValues()  e que representa o conteudo do registro a ser alterado ou criado
            //em um formato que o banco de dados entende.
            ContentValues valores = new ContentValues();
            //transfere os valores das variáveis de instancia do objeto para a variável valor na forma (chave, valor)
            valores.put(COLUNA1,lembrete.getTitle());
            valores.put(COLUNA2, lembrete.getDescription());
            valores.put(COLUNA3, lembrete.getDate());
            valores.put(COLUNA4, lembrete.getValid());
            if(id!=0){//se já existe este contato e queremos simplesmente atualizá-lo
                // String _id = String.valueOf(id);
                //String[] whereArgs = new String[]{_id};
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

}
