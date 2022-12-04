package com.lucasgteixeira.moviesmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucasgteixeira.moviesmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
    }
}