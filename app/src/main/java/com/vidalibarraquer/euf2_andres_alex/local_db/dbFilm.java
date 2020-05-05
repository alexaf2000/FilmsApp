package com.vidalibarraquer.euf2_andres_alex.local_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.vidalibarraquer.euf2_andres_alex.models.Film;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class dbFilm extends SQLiteOpenHelper {

    // Definitions
    private static final int DATABASE_VERSION = 1;
    // DB Name
    private static final String DATABASE_NAME = "appDB";
    // DB Table name
    private static final String TABLE_NAME = "films";
    // DB Properties
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_PUNTUATION = "puntuation";
    private static final String KEY_COVER = "cover";

    // Constructor
    public dbFilm(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database creation
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " TEXT PRIMARY KEY, " + KEY_TITLE + " TEXT, " + KEY_GENRE + " TEXT, " + KEY_DURATION + " INTEGER," + KEY_PUNTUATION + " INTEGER, " + KEY_COVER + " TEXT"  + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop if exists
        db.execSQL("DROP TABLE  IF EXISTS " + TABLE_NAME);
        // Let's create the table again
        onCreate(db);
    }

    public void addFilm(Film film){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Do the correspondencies
        values.put(KEY_ID, film.getId());
        values.put(KEY_TITLE, film.getTitle());
        values.put(KEY_GENRE, film.getGenre());
        values.put(KEY_DURATION, film.getDuration());
        values.put(KEY_PUNTUATION, film.getPuntuation());
        values.put(KEY_COVER, film.getCover().toString());

        //Add the register into the table
        db.insert(TABLE_NAME,null,values);
        System.out.println("This is the context: "+ film.getCover().toString());
        // Close the Database connection
        db.close();
    }

    // Return all Films
    public List<Film> getAllFilms()  {
        List<Film> filmsList = new ArrayList<Film>();

        // Query for getting all table registers
        String getAllQuery ="SELECT * FROM "+ TABLE_NAME;

        //Let's execute the query
        SQLiteDatabase db = this.getWritableDatabase();

        // Saves the results on the cursor
        Cursor cursor = db.rawQuery(getAllQuery, null);

        //Cursor movement
        if (cursor.moveToFirst()) {
            do{
                //Let's create the object Film
                Film film = new Film();
                // Let's do the correspondency with the data returned from db
                film.setId(cursor.getString(0));
                film.setTitle(cursor.getString(1));
                film.setGenre(cursor.getString(2));
                film.setDuration(Integer.parseInt(cursor.getString(3)));
                film.setPuntuation(Integer.parseInt(cursor.getString(4)));
                try {
                    film.setCover(new URL(cursor.getString(5)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //Add to the list the object film created
                filmsList.add(film);

            } while (cursor.moveToNext());
        }

        return filmsList;

    }
}
