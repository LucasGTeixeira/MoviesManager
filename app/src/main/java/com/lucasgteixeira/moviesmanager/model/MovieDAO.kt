package com.lucasgteixeira.moviesmanager.model

interface MovieDAO {
    fun createMovie(movie: Movie): Int
    fun retrieveMovie(id: Int): Movie?
    fun retrieveMovies(): MutableList<Movie>
    fun updateMovie(movie: Movie): Int
    fun deleteMovie(id: Int): Int
}