package com.example.migueltrabalho.Controle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.migueltrabalho.Modelo.Computador;
import com.example.migueltrabalho.Persistencia.ComputadorBD;
import com.example.migueltrabalho.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ManterComputador extends AppCompatActivity {

    TextView aliasId;
    EditText aliasCpu, aliasMem, aliasHd;

    ComputadorBD computadorBD;
    Computador computador;
    List<Computador> computadores;
    ImageView aliasfoto;
    private static final int CAMERA_REQUEST=123;
    private byte[] imagem=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_computador);
        aliasId=findViewById(R.id.textViewId);
        aliasCpu=findViewById(R.id.editTextcpu);
        aliasMem=findViewById(R.id.editTextmem);
        aliasHd=findViewById(R.id.editTexthd);
        aliasfoto=findViewById(R.id.imageView);

        computador=new Computador();
        computadorBD=ComputadorBD.getInstance(this);
        verificarParametro();
        aliasfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo =(Bitmap) data.getExtras().get("data");
            aliasfoto.setImageBitmap(photo);
            byte[] img=getBitmapAsByteArray(photo);
            imagem=img;
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }



    private void verificarParametro() {
        Intent intent=getIntent();
        if (intent.getSerializableExtra("Objeto")==null){
            computador=new Computador();
        }
        else{
            computador=(Computador)intent.getSerializableExtra("Objeto");
            aliasId.setText(computador.get_id().toString());
            aliasCpu.setText(computador.getProcessador());
            aliasMem.setText(computador.getMemoria());
            aliasHd.setText(computador.getArmazenamento());

            carregarimagem();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menumanter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id==R.id.itemSalvar){
            salvar();
        }
        else if (id==R.id.itemLocalizar){
            localizar();
        }
        else if (id==R.id.itemExcluir) {
            deletar();
        }
        return true;
    }


    private void salvar() {
        if (computador.get_id()==null){
            computador=new Computador();
        }
        computador.setProcessador(aliasCpu.getText().toString());
        computador.setMemoria(aliasMem.getText().toString());
        computador.setImagemAtributoModelo(imagem); // aqui colocamos a imagem convertida. se o usuário não escolher ela
        // vem como nula na sua declaração
        computador.setArmazenamento(aliasHd.getText().toString());

        if (computadorBD.save(computador)!=0){
            Toast.makeText(ManterComputador.this, "Salvo com Sucesso "+computador.toString(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ManterComputador.this, "Erro no Banco de Dados ", Toast.LENGTH_SHORT).show();
        }
    }


    private void localizar() {
        computadores=computadorBD.getByname(aliasCpu.getText().toString());

        if (computadores.size()>0){
            computador = new Computador();
            // esta linha substitui as linhas de atributo por atgributo
            computador=computadores.get(0);
            // Mostra objeto na visão
            aliasId.setText(computador.get_id().toString());
            aliasCpu.setText(computador.getProcessador());
            aliasMem.setText(computador.getMemoria());
            aliasHd.setText(computador.getArmazenamento());
            // estas linha dabaixo realizam a conversão da imagem do modelo que está em array de bytes para
            // bitmap para mostrar para o usuário na visão.
            carregarimagem();
        }
        else{Toast.makeText(this, "Nenhum item encontrado", Toast.LENGTH_SHORT).show();}

    }

    // método utilizado para mostrar a imagem na visão, é utilizado sempre que
    // a imagem precisar ser mostrada ao usuário.


    private void carregarimagem() {
        if(computador.getImagemAtributoModelo()==null){
            imagem=null;
        }
        else
        {
            Bitmap bitmap= BitmapFactory.decodeByteArray(
                    computador.getImagemAtributoModelo(),0,computador.getImagemAtributoModelo().length);
            aliasfoto.setImageBitmap(bitmap);
            // além de mostrar para o usuário passa a imagem do modelo para a variável imagem, para que quando
            // o usuário clicar em salvar sem alterar a imagem, ela retorne para o banco...
            byte [] img = getBitmapAsByteArray(bitmap);
            imagem=img;
        }

    }


    private void deletar() {
        if (computador.get_id()==null){
            Toast.makeText(this, "Localize um computador primeiro", Toast.LENGTH_SHORT).show();
        }
        else
        {
            computadorBD.delete(computador);
            Toast.makeText(this, "computador Excluido", Toast.LENGTH_SHORT).show();
            limparcampos();

        }
    }


    private void limparcampos() {
        aliasId.setText("");
        aliasCpu.setText("");
        aliasMem.setText("");
        aliasHd.setText("");
        computador=new Computador();
    }
}

