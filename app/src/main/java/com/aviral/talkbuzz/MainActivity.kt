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
     *      2.] Welcome E-mail Implementation. Library: https://github.com/yesidlazaro/GmailBackground
     *      3.] Repository logic implementation in each and every view model possible.
     *              ViewModels                  Status
     *          1.] Login View Model            Done
     *          2.] Register View Model         Done
     *          3.] Channel View Model          Not Yet
     *      4.] Do the setting in stream dashboard so that app doesn't crash any how.
     *      5.] Do testing of the application.
     *      6.] Generate a good looking Readme.MD file.
     *      7.] Add TalkBuzz to the pins project in the github.
     *      8.] Add TalkBuzz as the project (Dec - Mar) and do post for this project.
     * */

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}