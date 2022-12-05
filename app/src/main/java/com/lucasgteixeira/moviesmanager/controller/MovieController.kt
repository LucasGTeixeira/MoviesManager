package com.lucasgteixeira.moviesmanager.controller

import com.lucasgteixeira.moviesmanager.model.Movie
import com.lucasgteixeira.moviesmanager.model.MovieDAO
import com.lucasgteixeira.moviesmanager.model.MovieDAOSQLite
import com.lucasgteixeira.moviesmanager.view.MainActivity

class MovieController(mainActivity: MainActivity) {
    private val movieDAOImp: MovieDAO = MovieDAOSQLite(mainActivity)

    fun insertMovie(movie: Movie) = movieDAOImp.createMovie(movie)
    fun getMovie(id: Int) = movieDAOImp.retrieveMovie(id)
    fun getMovies() = movieDAOImp.retrieveMovies()
    fun editMovie(movie: Movie) = movieDAOImp.updateMovie(movie)
    fun removeMovie(id: Int) = movieDAOImp.deleteMovie(id)
}