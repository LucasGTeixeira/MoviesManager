package com.lucasgteixeira.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.lucasgteixeira.moviesmanager.R

import com.lucasgteixeira.moviesmanager.adapter.MovieAdapter
import com.lucasgteixeira.moviesmanager.databinding.ActivityMainBinding
import com.lucasgteixeira.moviesmanager.model.Constant.EXTRA_LIST_MOVIE_NAMES
import com.lucasgteixeira.moviesmanager.model.Constant.EXTRA_MOVIE
import com.lucasgteixeira.moviesmanager.model.Constant.VIEW_MOVIE

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

        marl = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),) {
                result ->
            if (result.resultCode == RESULT_OK) {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)

                movie?.let { _movie->
                    val position = movieList.indexOfFirst { it.id == _movie.id }
                    if (position != -1) {
                        movieList[position] = _movie
                    }
                    else {
                        movieList.add(movie)
                    }
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }
        registerForContextMenu(amb.movieLv)

        amb.movieLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val movie = movieList[position]
                val movieIntent = Intent(this@MainActivity, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, true)
                startActivity(movieIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addMovieMi -> {
                val movieIntent = Intent(this@MainActivity, MovieActivity::class.java)
                val nameList = movieList.map { it.name }
                movieIntent.putStringArrayListExtra(EXTRA_LIST_MOVIE_NAMES, ArrayList(nameList))
                marl.launch(movieIntent)
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.removeMovieMi -> {
                movieList.removeAt(position)
                movieAdapter.notifyDataSetChanged()
                true
            }
            R.id.editMovieMi -> {
                val movie = movieList[position]
                val movieIntent = Intent(this, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, false)
                marl.launch(movieIntent)
                true
            }
            else -> { false }
        }
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
                    flag = "checked",
                    rating = "5",
                    genra = "terror",
                )
            )
        }
    }
}