package com.lucasgteixeira.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.lucasgteixeira.moviesmanager.databinding.ActivityMovieBinding
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

        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let{ _receiveMovie ->
            with(amb) {
                with(_receiveMovie) {
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
            amb.studioEt.isEnabled = false
            amb.ratingEt.isEnabled = false
            amb.saveBt.visibility = View.GONE
        }

        amb.saveBt.setOnClickListener {
            val flagValue : String = if(amb.flagSw.isChecked) "checked" else "unchecked"

            val person = Movie(
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
            resultIntent.putExtra(EXTRA_MOVIE, person)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}