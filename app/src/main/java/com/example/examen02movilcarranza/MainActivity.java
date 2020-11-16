package com.example.examen02movilcarranza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examen02movilcarranza.api.AutorApi;
import com.example.examen02movilcarranza.api.PaisApi;
import com.example.examen02movilcarranza.connection.ConnectionApi;
import com.example.examen02movilcarranza.entity.Autor;
import com.example.examen02movilcarranza.entity.Pais;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> lista = new ArrayList<String>();
    ArrayAdapter<String> adaptador = null;
    Spinner spnPais = null;
    EditText edtNombres, edtAPaterno,edtAMaterno;
    Button btnEnviar;
    PaisApi apiPais = null;
    AutorApi apiAutor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNombres=(EditText)findViewById(R.id.txt_nombres);
        edtAPaterno=(EditText)findViewById(R.id.txt_apaterno);
        edtAMaterno=(EditText)findViewById(R.id.txt_amaterno);
        spnPais=(Spinner)findViewById(R.id.spnPais);
        btnEnviar=(Button)findViewById(R.id.btnRegistrar);

        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lista);
        spnPais.setAdapter(adaptador);

        //Conexion al servicio
        apiPais= ConnectionApi.getConnection().create(PaisApi.class);
        apiAutor=ConnectionApi.getConnection().create(AutorApi.class);

        listaPaises();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = edtNombres.getText().toString().trim();
                String aPaterno = edtAPaterno.getText().toString().trim();
                String aMaterno = edtAMaterno.getText().toString().trim();
                int idPais = spnPais.getSelectedItemPosition();

                if(nom.equals("")){
                    mensaje("Colocar nombres");
                    edtNombres.requestFocus();
                }else if(aPaterno.equals("")){
                    mensaje("Colocar apellido paterno");
                    edtAPaterno.requestFocus();
                }else if(aMaterno.equals("")){
                    mensaje("Colocar apellido materno");
                    edtAMaterno.requestFocus();
                }else {
                    Pais pais = new Pais();
                    pais.setIdPais(idPais+1);

                    Autor autor = new Autor();
                    autor.setNombres(nom);
                    autor.setApaterno(aPaterno);
                    autor.setAmaterno(aMaterno);
                    autor.setIdPais(pais);

                    insertarAutor(autor);
                    limpiarCajas();
                }

                }

        });


    }
    void limpiarCajas(){
        edtNombres.setText("");
        edtAPaterno.setText("");
        edtAMaterno.setText("");
        edtNombres.requestFocus();
    }

    void mensajeAlerta(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("--- MENSAJE ---");
        alerta.setMessage("Registro ingresado");
        alerta.setPositiveButton("Aceptar",null);
        alerta.create();
        alerta.show();
    }
    void insertarAutor(Autor autor){
        Call<Autor>call = apiAutor.insertaAutor(autor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    mensajeAlerta();
                }else{
                    mensaje("No registró");
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensaje("Error: "+t.getMessage());
            }
        });
    }
    void listaPaises(){
        Call<List<Pais>>call=apiPais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> salida=response.body();
                    for(Pais x:salida){
                        lista.add(x.getNombre()+" ("+x.getIso()+")");
                    }
                    adaptador.notifyDataSetChanged();
                }else{
                    mensaje("Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensaje("Error: "+t.getMessage());
            }
        });
    }

    void mensaje(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }
}