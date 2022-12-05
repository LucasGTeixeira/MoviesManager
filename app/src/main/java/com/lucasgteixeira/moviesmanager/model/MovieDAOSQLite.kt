package com.lucasgteixeira.moviesmanager.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.lucasgteixeira.moviesmanager.R
import java.sql.SQLException

class MovieDAOSQLite(context: Context) : MovieDAO {

    companion object Constant {
        private const val MOVIE_DATABASE_FILE = "movies"
        private const val MOVIE_TABLE = "movie"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val RELEASE_YEAR_COLUMN = "releaseYear"
        private const val STUDIO_COLUMN = "studio"
        private const val DURATION_COLUMN = "duration"
        private const val FLAG_COLUMN = "flag"
        private const val RATING_COLUMN = "rating"
        private const val GENRA_COLUMN = "genra"

        private const val CREATE_MOVIE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $MOVIE_TABLE ( " +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT NOT NULL, " +
                    "$RELEASE_YEAR_COLUMN TEXT NOT NULL, " +
                    "$STUDIO_COLUMN TEXT NOT NULL, " +
                    "$DURATION_COLUMN TEXT NOT NULL, "+
                    "$FLAG_COLUMN TEXT NOT NULL, "+
                    "$RATING_COLUMN TEXT NOT NULL, "+
                    "$GENRA_COLUMN TEXT NOT NULL );"
    }

    private val movieSqliteDatabase: SQLiteDatabase

    init {
        movieSqliteDatabase = context.openOrCreateDatabase(
            MOVIE_DATABASE_FILE,
            MODE_PRIVATE,
            null
        )
        try {
            movieSqliteDatabase.execSQL(CREATE_MOVIE_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }

    private fun Movie.toContentValues() = with(ContentValues()) {
        put(NAME_COLUMN, name)
        put(RELEASE_YEAR_COLUMN, releaseYear)
        put(STUDIO_COLUMN, studio)
        put(DURATION_COLUMN, duration)
        put(FLAG_COLUMN, flag)
        put(RATING_COLUMN, rating)
        put(GENRA_COLUMN, genra)
        this
    }

    private fun Cursor.rowToMovie() = Movie(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NAME_COLUMN)),
        getString(getColumnIndexOrThrow(RELEASE_YEAR_COLUMN)),
        getString(getColumnIndexOrThrow(STUDIO_COLUMN)),
        getString(getColumnIndexOrThrow(DURATION_COLUMN)),
        getString(getColumnIndexOrThrow(FLAG_COLUMN)),
        getString(getColumnIndexOrThrow(RATING_COLUMN)),
        getString(getColumnIndexOrThrow(GENRA_COLUMN))
    )

    override fun createMovie(movie: Movie) = movieSqliteDatabase.insert(
        MOVIE_TABLE,
        null,
        movie.toContentValues()
    ).toInt()


    override fun retrieveMovie(id: Int): Movie? {
        val cursor = movieSqliteDatabase.rawQuery(
            "SELECT * FROM $MOVIE_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )
        val movie = if (cursor.moveToFirst()) cursor.rowToMovie() else null

        cursor.close()
        return movie
    }

    override fun retrieveMovies(): MutableList<Movie> {
        val movieList = mutableListOf<Movie>()
        val cursor = movieSqliteDatabase.rawQuery(
            "SELECT * FROM $MOVIE_TABLE ORDER BY $NAME_COLUMN",
            null
        )
        while (cursor.moveToNext()) {
            movieList.add(cursor.rowToMovie())
        }
        cursor.close()
        return movieList
    }

    override fun updateMovie(movie: Movie) = movieSqliteDatabase.update(
        MOVIE_TABLE,
        movie.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(movie.id.toString())
    )

    override fun deleteMovie(id: Int) =
        movieSqliteDatabase.delete(
            MOVIE_TABLE,
            "$ID_COLUMN = ?",
            arrayOf(id.toString())
        )
}