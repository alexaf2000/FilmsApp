package com.vidalibarraquer.euf2_andres_alex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vidalibarraquer.euf2_andres_alex.local_db.dbFilm;
import com.vidalibarraquer.euf2_andres_alex.models.Film;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ModifyFilmActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleEdit, genreEdit, durationEdit, puntuationEdit;
    ImageView coverPreviewImage;
    Button saveModificationBtn;

    dbFilm films_db;
    Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_film);

        titleEdit = (EditText) findViewById(R.id.titleEdit);
        genreEdit = (EditText) findViewById(R.id.genreEdit);
        durationEdit = (EditText) findViewById(R.id.durationEdit);
        puntuationEdit = (EditText) findViewById(R.id.puntuationEdit);


        coverPreviewImage = (ImageView) findViewById(R.id.coverPreviewImage);
        coverPreviewImage.setOnClickListener(this);

        saveModificationBtn = (Button) findViewById(R.id.saveModificationBtn);
        saveModificationBtn.setOnClickListener(this);

        films_db = new dbFilm(this);

        this.film = films_db.getFilm(getIntent().getStringExtra("id"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(film.getTitle());

        titleEdit.setText(film.getTitle());
        genreEdit.setText(film.getGenre());
        durationEdit.setText(String.valueOf(film.getDuration()));
        puntuationEdit.setText(String.valueOf(film.getPuntuation()));

        Glide.with(coverPreviewImage.getContext())
                .load(film.getCover().toString())
                .into(coverPreviewImage);
    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modify_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteBtn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Eliminar pelicula");
                builder.setMessage("Estás seguro de que deseas eliminar esta pelicula?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                films_db.removeFilm(film.getId());
                                Toast.makeText(ModifyFilmActivity.this, "La pelicula " + film.getTitle() + " ha sido eliminada.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", null);
                builder.show();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.coverPreviewImage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Modificar portada");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Dirección URL de la imagen");
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        film.setCover(new URL(input.getText().toString()));
                        Glide.with(coverPreviewImage.getContext())
                                .load(input.getText().toString())
                                .into(coverPreviewImage);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        if (v.getId() == R.id.saveModificationBtn) {
            Film modifiedFilm = new Film();
            modifiedFilm.setTitle(titleEdit.getText().toString());
            modifiedFilm.setGenre(genreEdit.getText().toString());
            modifiedFilm.setPuntuation(Integer.valueOf(puntuationEdit.getText().toString()));
            modifiedFilm.setDuration(Integer.valueOf(durationEdit.getText().toString()));
            modifiedFilm.setCover(film.getCover());

            films_db.modifyFilm(film.getId(), modifiedFilm);
            Toast.makeText(ModifyFilmActivity.this, "Pelicula " + modifiedFilm.getTitle() + " modificada correctamente.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
