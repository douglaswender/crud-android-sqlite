package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public PessoaDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase(); //criando conexao e pegando banco de dados
    }

    public long inserirPessoa(Pessoa pessoa){
        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("idade", pessoa.getIdade());
        values.put("endereco", pessoa.getEndereco());

        return banco.insert("pessoa", null, values);
    }

    public void delete(Pessoa pessoa){
        banco.delete("pessoa","id = ?", new String[]{pessoa.getId().toString()});
    }

    public void upDate(Pessoa pessoa){
        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("idade", pessoa.getIdade());
        values.put("endereco", pessoa.getEndereco());
        banco.update("pessoa", values, "id = ?", new String[]{pessoa.getId().toString()});
    }

    public List<Pessoa> getAll(){
        List<Pessoa> pessoasLista = new ArrayList<>();
        Cursor cursor = banco.query("pessoa", new String[]{"id","nome","idade","endereco"},
                null ,null, null, null, null);
        while(cursor.moveToNext()){
            Pessoa p1 = new Pessoa();
            p1.setId(cursor.getInt(0));
            p1.setNome(cursor.getString(1));
            p1.setIdade(cursor.getString(2));
            p1.setEndereco(cursor.getString(3));

            pessoasLista.add(p1);
        }
        return pessoasLista;
    }
}
