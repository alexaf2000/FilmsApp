package com.vidalibarraquer.euf2_andres_alex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vidalibarraquer.euf2_andres_alex.local_db.dbFilm;
import com.vidalibarraquer.euf2_andres_alex.models.Film;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, filmsAdapter.ItemClickListener {
    // Visual components assignation

    FloatingActionButton floatingActionButton;
    RecyclerView filmsRecyclerView;

    GridLayoutManager layoutManager;
    dbFilm film_db;
    ArrayList<HashMap<String, String>> filmsDataSet;
    List<Film> filmsList;
    filmsAdapter mAdapter;

    SharedPreferences preferences;
    String GenreFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign visual component to the variable
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        filmsRecyclerView = (RecyclerView) findViewById(R.id.filmsRecyclerView);
        // Let's put a onClick Listener
        floatingActionButton.setOnClickListener(this);


        //Mejora el rendimiento sabiendo que el tamaño de los childs será fijo
        filmsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(this, 3);

        filmsRecyclerView.setLayoutManager(layoutManager);


        // Let's use the db
        film_db = new dbFilm(MainActivity.this);

        // Read sharedpreferences from this app
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        GenreFilter = preferences.getString("GenreFilter", null);
        Toast.makeText(this, GenreFilter + "", Toast.LENGTH_SHORT).show();

        // If not saved before default films then...
        if (preferences.getBoolean("storedDefaultValues", false) == false) {
            // Save as now the default films are inserted
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("storedDefaultValues", true);
            editor.commit();

            // Creates multiple Film objects and add it to the DB
            try {
                film_db.addFilm(new Film("Star Wars Episodio III: La venganza de los sith", "Ciencia Ficción", 160, 8, new URL("https://assets.cinepolisklic.com/cmsklicia/movieimages/star-wars-episodio-iii-la-venganza-de-los-sith/poster_originalsize_250X375.png")));
                film_db.addFilm(new Film("Harry Potter y el prisionero de Azkaban", "Ciencia Ficción", 180, 7, new URL("https://sun6-3.userapi.com/c831109/v831109559/1c7f6a/gem24ZAlE60.jpg")));
                film_db.addFilm(new Film("Corazones de acero", "Bélica", 140, 8, new URL("https://2.bp.blogspot.com/-h8AqR4wo7Kc/VL4N8-6HpoI/AAAAAAAAHv4/ROsqLnKyY-0/s1600/581497.jpg")));
                film_db.addFilm(new Film("Operación Valkiria", "Bélica", 160, 8, new URL("https://i.pinimg.com/originals/db/88/24/db8824878921d6e1d1599cf973c38655.jpg")));
                film_db.addFilm(new Film("Los Vengadores: Endgame", "Superheroes", 120, 7, new URL("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQujAu6RsAWa1Wd-jdlI5ScYSt6-qAGjX8sGGmkHdesGK5LXQOv")));
                film_db.addFilm(new Film("The Amazing Spider-man", "Superheroes", 145, 6, new URL("https://vignette.wikia.nocookie.net/spiderman/images/f/f9/The_Amazing_Spider_Man_Poster.png/revision/latest?cb=20131213215215&path-prefix=es")));
                film_db.addFilm(new Film("Salidos de cuenta", "Comedia", 120, 8, new URL("https://i.pinimg.com/originals/4f/22/81/4f22811715a4d6716a6db9511d74fb74.jpg")));
                film_db.addFilm(new Film("La lista de Schindler", "Drama", 120, 9, new URL("https://4.bp.blogspot.com/-SXXx3Z7WDBQ/VumyL_SZHLI/AAAAAAAABRY/vLv9CQvfrhcuBRoRXioMg5tc5XML8dBvQ/s1600/hpk83.jpg")));
                film_db.addFilm(new Film("Resacón en las Vegas", "Comedia", 90, 8, new URL("https://static.carrefour.es/hd_510x_/imagenes/products/84365/34535/527/8436534535527/imagenGrande1.jpg")));
                film_db.addFilm(new Film("Winchester", "Terror", 120, 8, new URL("https://comoacaba.com/wp-content/uploads/2019/11/A2mSogbwEcKqMcO0MQOEZPAjK8r.jpg")));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        // Once ended the start let's refresh and load all the films data
        refreshData();
        // Especifica el adaptador a fer servir
        mAdapter = new filmsAdapter(MainActivity.this, filmsDataSet);
        mAdapter.setClickListener(this);

        filmsRecyclerView.setAdapter(mAdapter);
        ;
    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshData();

        mAdapter.setItems(filmsDataSet);
        mAdapter.notifyDataSetChanged();
    }

    protected void refreshData() {
        if (GenreFilter != "Todas") {
            filmsList = film_db.getAllFilmsByGenre(GenreFilter);
        } else {
            filmsList = film_db.getAllFilms();
        }

        HashMap<String, String> hashMap;
        filmsDataSet = new ArrayList<HashMap<String, String>>();

        for (Film film : filmsList) {
            String id = film.getId();
            String title = film.getTitle();
            String cover = film.getCover().toString();


            //Afegim la clau valor a un objecte de tipus Hashmap
            hashMap = new HashMap<String, String>();
            hashMap.put("id", id);
            hashMap.put("title", title);
            hashMap.put("cover", cover);

            filmsDataSet.add(hashMap);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                // Load AddFilm Activity
                Intent intent = new Intent(this, AddFilmActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        // If we pressed a film then...
        // Creates a new intent
        Intent intent = new Intent(this, ModifyFilmActivity.class);
        // Put as param of the intent called id the film id
        intent.putExtra("id", filmsDataSet.get(position).get("id"));
        // Start the activity
        startActivity(intent);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filterButton) {
            // Will get the list of genres
            final List<String> StringList = film_db.getAllGenres();
            // Convert the string array to a CharSequence (required by AlertDialog)
            CharSequence[] cs = StringList.toArray(new CharSequence[StringList.size()]);
            // Creates de AlertDialogBuilder
            new AlertDialog.Builder(this)
                    .setTitle("Filtrar por genero")
                    .setItems(cs, new DialogInterface.OnClickListener() {
                        // On click on genre
                        public void onClick(DialogInterface dialog, int position) {
                            GenreFilter = StringList.get(position);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("GenreFilter", GenreFilter);
                            editor.commit();
                            onStart();
                            Toast.makeText(MainActivity.this, "Te has suscrito a las notificaciones de " + GenreFilter, Toast.LENGTH_LONG).show();

                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

}
