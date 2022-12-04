package com.lucasgteixeira.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lucasgteixeira.moviesmanager.databinding.ActivityMovieBinding
import com.lucasgteixeira.moviesmanager.model.Constant.EXTRA_LIST_MOVIE_NAMES
import com.lucasgteixeira.moviesmanager.model.Constant.EXTRA_MOVIE
import com.lucasgteixeira.moviesmanager.model.Constant.VIEW_MOVIE
import com.lucasgteixeira.moviesmanager.model.Genra
import com.lucasgteixeira.moviesmanager.model.Movie
import kotlin.random.Random

class MovieActivity : AppCompatActivity(){
    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        val listMovies = intent.getStringArrayListExtra(EXTRA_LIST_MOVIE_NAMES)
        Log.i("log", listMovies?.size.toString())

        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let{ _receiveMovie ->
            with(amb) {
                with(_receiveMovie) {
                    nameEt.isEnabled = false
                    nameEt.setText(name)
                    durationEt.setText(duration)
                    releaseYearEt.setText(releaseYear)
                    if(flag == "checked") flagSw.toggle()
                    studioEt.setText(studio)
                    ratingEt.setText(rating)
                    for (i in 0 until Genra.values().size){
                        if(genra == Genra.values()[i].toString()) {
                            genraSp.setSelection(i)
                        }
                    }
                }
            }
        }
        val viewMovie = intent.getBooleanExtra(VIEW_MOVIE, false)
        if (viewMovie) {
            amb.nameEt.isEnabled = false
            amb.releaseYearEt.isEnabled = false
            amb.durationEt.isEnabled = false
            amb.studioEt.isEnabled = false
            amb.flagSw.isEnabled = false
            amb.ratingEt.isEnabled = false
            amb.genraSp.isEnabled = false
            amb.saveBt.visibility = View.GONE
        }

        amb.saveBt.setOnClickListener {
            val flagValue : String = if(amb.flagSw.isChecked) "checked" else "unchecked"

            if (listMovies != null) {
                Log.i("log", "listMovieNotNull")
                for (i in 0 until listMovies.size){
                    Log.i("log", "entrou no for")
                    if(listMovies[i] == amb.nameEt.text.toString()) {
                        Toast.makeText(this, "nome já cadastrado", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            val movie = Movie(
                id = receivedMovie?.id?: Random(System.currentTimeMillis()).nextInt(),
                name = amb.nameEt.text.toString(),
                duration = amb.durationEt.text.toString(),
                releaseYear = amb.releaseYearEt.text.toString(),
                studio = amb.studioEt.text.toString(),
                flag = flagValue,
                rating = amb.ratingEt.text.toString(),
                genra = amb.genraSp.selectedItem.toString()
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MOVIE, movie)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}