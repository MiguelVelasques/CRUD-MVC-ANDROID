package com.example.migueltrabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.migueltrabalho.Controle.ListarComputador;
import com.example.migueltrabalho.Controle.ManterComputador;


public class MainActivity extends AppCompatActivity {

    private String[] menu = new String[]{"Manter Computador", "Listar Computador", "Sair"};
    ListView aliasmenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aliasmenu=findViewById(R.id.listamenu);

        ArrayAdapter<String> adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,menu);

        aliasmenu.setAdapter(adaptador);

        aliasmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getBaseContext(), ManterComputador.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getBaseContext(), ListarComputador.class);
                        startActivity(intent2);
                        break;
                    case 2: finish();

                        break;
                    default:break;
                }
            }
        });


    }
}
