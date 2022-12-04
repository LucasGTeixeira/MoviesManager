package com.lucasgteixeira.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.result.ActivityResultLauncher

import com.lucasgteixeira.moviesmanager.adapter.MovieAdapter
import com.lucasgteixeira.moviesmanager.databinding.ActivityMainBinding

import com.lucasgteixeira.moviesmanager.model.Movie

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val movieList: MutableList<Movie> = mutableListOf()

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var marl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillMovieList()

        movieAdapter = MovieAdapter(this, movieList)
        amb.movieLv.adapter = movieAdapter

        movieAdapter.notifyDataSetChanged()


    }

    private fun fillMovieList() {
        for (i in 1..5) {
            movieList.add(
                Movie(
                    id = i,
                    name = "filme $i",
                    releaseYear = "20$i",
                    studio = "studio",
                    duration = "60",
                    flag = "assitido",
                    rating = "5",
                    genra = "terror",
                )
            )
        }
    }
}