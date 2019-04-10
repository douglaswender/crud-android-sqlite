package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    EditText nome, idade, endereco;
    Button btnCriar;
    PessoaDAO dao;
    Pessoa pessoaUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("DATA: ", String.valueOf(Environment.getDataDirectory()));
        nome = (EditText) findViewById(R.id.editNome);
        idade = (EditText) findViewById(R.id.editIdade);
        endereco = (EditText) findViewById(R.id.editEndereco);

        btnCriar = (Button) findViewById(R.id.btnCriar);

        dao = new PessoaDAO(this);

        Intent it = getIntent();

        if (it.hasExtra("pessoa")){

            pessoaUpdate = (Pessoa) it.getSerializableExtra("pessoa");

            Log.d("teste", "Idade: "+pessoaUpdate.getIdade());
            Log.d("teste", "Endereço: "+pessoaUpdate.getEndereco());
            Log.d("teste", "Nome: "+ pessoaUpdate.getNome());
            nome.setText(pessoaUpdate.getNome().toString());
            idade.setText(pessoaUpdate.getIdade().toString());
            endereco.setText(pessoaUpdate.getEndereco().toString());
        }


    }
    public void salvar(View view) throws IOException {

        if (pessoaUpdate == null) {

            Pessoa pessoa = new Pessoa();

            if(nome.getText().toString().isEmpty()||idade.getText().toString().isEmpty()||endereco.getText().toString().isEmpty()){

                Toast toast = Toast.makeText(this, "Nenhum campo pode ficar vazio!", LENGTH_SHORT);
                toast.show();

            }else{
                pessoa.setNome(nome.getText().toString());
                pessoa.setIdade(idade.getText().toString());
                pessoa.setEndereco(endereco.getText().toString());
                long id = dao.inserirPessoa(pessoa);

                EnviarEmail em = new EnviarEmail();

                em.enviar(pessoa);

                Toast toast = Toast.makeText(this, "Inserção concluida com id: " + id, LENGTH_SHORT);
                toast.show();
                nome.setText("");
                idade.setText("");
                endereco.setText("");

                Intent it = new Intent(MainActivity.this, ListarPessoasActivity.class);
                startActivity(it);
            }




        }else{

            pessoaUpdate.setNome(nome.getText().toString());
            pessoaUpdate.setIdade(idade.getText().toString());
            pessoaUpdate.setEndereco(endereco.getText().toString());
            dao.upDate(pessoaUpdate);
            Toast toast = Toast.makeText(this, "Atualização concluida ", LENGTH_SHORT);
            toast.show();

            Intent it = new Intent(MainActivity.this, ListarPessoasActivity.class);
            startActivity(it);

        }
    }

}
