package com.aviral.talkbuzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aviral.talkbuzz.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * TODO:-
     *      1.] Splash Screen Implementation.
     *      2.] Welcome E-mail Implementation.
     *      3.] Repository logic implementation in each and every view model possible.
     * */

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}