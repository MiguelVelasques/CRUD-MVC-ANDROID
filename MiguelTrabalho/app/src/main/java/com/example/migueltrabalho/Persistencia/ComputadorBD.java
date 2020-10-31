package com.example.migueltrabalho.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.migueltrabalho.Modelo.Computador;

import java.util.ArrayList;
import java.util.List;

public class ComputadorBD extends SQLiteOpenHelper {
    private static final String NOME_BD = "computadornovo.sqlite";
    private static final int VERSAO = 2;
    private static ComputadorBD computadorBD= null; //Singleton

    //Construtor
    public ComputadorBD(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BD, null, VERSAO);
    }

    // utilizado para instanciar a classe, note o IF
    public static ComputadorBD getInstance(Context context){
        if(computadorBD == null){
            computadorBD = new ComputadorBD(context);
            return computadorBD;
        }else{
            return computadorBD;
        }    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists computador" +
                "( _id integer primary key autoincrement, " +
                " processador text, " +
                " memoria text, " +
                " imagemAtributoBanco blob," +
                " armazenamento text);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // aqui drop tables chama Oncreate
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS computador");
        onCreate(sqLiteDatabase); // chama onCreate e recria o banco de dados
    }

    public long save(Computador computador){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{

            ContentValues values = new ContentValues();
            values.put("processador", computador.getProcessador());
            values.put("memoria", computador.getMemoria());
            values.put("imagemAtributoBanco", computador.getImagemAtributoModelo());
            values.put("armazenamento", computador.getArmazenamento());
            if(computador.get_id() == null){
                //insere no banco de dados
                return db.insert("computador", null, values);
            }else
            {//altera no banco de dados
                values.put("_id", computador.get_id());
                return db.update("computador", values, "_id=" + computador.get_id(), null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {db.close(); }
        return 0;
    }


    public List<Computador> getAll(){
        SQLiteDatabase db = getReadableDatabase();
        try {

            return toList(db.rawQuery("SELECT  * FROM computador", null));
        } finally {
            db.close();
        }
    }

    public List<Computador> getByname(String processador){
        SQLiteDatabase db = getReadableDatabase();
        try {

            return toList(db.rawQuery("SELECT  * FROM computador where processador LIKE'" + processador + "%'", null));
        } finally {
            db.close();
        }
    }

    //converte de Cursor para List
    private List<Computador> toList(Cursor c) {
        List<Computador> computadors = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Computador computador = new Computador();
                // recupera os atributos do cursor para o conta
                computador.set_id(c.getLong(c.getColumnIndex("_id")));
                computador.setProcessador(c.getString(c.getColumnIndex("processador")));
                computador.setMemoria(c.getString(c.getColumnIndex("memoria")));
                computador.setImagemAtributoModelo(c.getBlob(c.getColumnIndex("imagemAtributoBanco")));
                computador.setArmazenamento(c.getString(c.getColumnIndex("armazenamento")));

                computadors.add(computador);
            } while (c.moveToNext());
        }
        return computadors;
    }

    public long delete(Computador computador){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            return db.delete("computador", "_id=?", new String[]{String.valueOf(computador.get_id())});
        }

        finally {
            db.close();
        }

    }

}
