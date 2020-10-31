package com.example.migueltrabalho.Controle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.migueltrabalho.Modelo.Computador;
import com.example.migueltrabalho.Persistencia.ComputadorBD;
import com.example.migueltrabalho.R;

import java.util.List;

public class ListarComputador extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView aliaslistview;
    ComputadorBD computadorBD;
    List<Computador> computadores;
    Computador computador;
    ArrayAdapter<Computador> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_computador);

        aliaslistview=findViewById(R.id.listviewlistar);
        computadorBD=ComputadorBD.getInstance(this);
        computadores=computadorBD.getAll();
        adaptador=new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, computadores);
        aliaslistview.setAdapter(adaptador);
        aliaslistview.setOnItemClickListener(this);
        computador=new Computador();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menulistagem, menu);
        MenuItem item=menu.findItem(R.id.itemSearch);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(onSearch());
        return true;
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptador.getFilter().filter(newText);
                return false;
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        computador=(Computador) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, ManterComputador.class);
        intent.putExtra("Objeto",computador); // SERIALIZABLE
        startActivity(intent);
    }
}
