package com.vidalibarraquer.euf2_andres_alex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vidalibarraquer.euf2_andres_alex.local_db.dbFilm;
import com.vidalibarraquer.euf2_andres_alex.models.Film;

import java.net.MalformedURLException;
import java.net.URL;

public class AddFilmActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleCreate, genreCreate, durationCreate, puntuationCreate, coverCreate;
    Button createBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        titleCreate = findViewById(R.id.titleCreate);
        genreCreate = findViewById(R.id.genreCreate);
        durationCreate = findViewById(R.id.durationCreate);
        puntuationCreate = findViewById(R.id.puntuationCreate);
        coverCreate = findViewById(R.id.coverCreate);

        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(this);


        getSupportActionBar().setTitle("Añadir nueva pelicula");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean NotEmptyTextEdits() {
        System.out.println(titleCreate.getText().toString().trim());
        if (titleCreate.getText().toString().trim().length() == 0 ||
                genreCreate.getText().toString().trim().length() == 0 ||
                durationCreate.getText().toString().trim().length() == 0 ||
                puntuationCreate.getText().toString().trim().length() == 0 ||
                coverCreate.getText().toString().trim().length() == 0)
            return false;
        else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createBtn) {
            System.out.println("El resultado es: " + NotEmptyTextEdits());
            if (NotEmptyTextEdits()) {
                try {
                    dbFilm films_db = new dbFilm(this);

                    Film film = new Film();

                    film.setTitle(titleCreate.getText().toString());
                    film.setGenre(genreCreate.getText().toString());

                    film.setCover(new URL(coverCreate.getText().toString()));
                    film.setDuration(Integer.valueOf(durationCreate.getText().toString()));
                    film.setPuntuation(Integer.valueOf(puntuationCreate.getText().toString()));

                    films_db.addFilm(film);

                    Toast.makeText(this, "La pelicula " + film.getTitle() + " ha sido añadida correctamente.", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (MalformedURLException e) {
                    Toast.makeText(this, "La URL introducida no es válida.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Debes de rellenar todos los campos.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
