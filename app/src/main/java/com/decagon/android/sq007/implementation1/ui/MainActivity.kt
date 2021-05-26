package com.decagon.android.sq007.implementation1.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Remove status bar during splash
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Load animation
        binding.SplashImage.animation = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation)
        binding.splashWelcomeText.animation = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animatioin)


        //start new Activity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, PostListActivity::class.java))
            finish() }, 3000)
        //Remove status bar
        supportActionBar?.hide()


    }
}