package com.example.socialsitelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView

class GloadActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long=2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gload)
        val lottieAnimation = findViewById<LottieAnimationView>(R.id.lottie)
        lottieAnimation.animate().translationY(1200f).setDuration(1000).setStartDelay(4000)


        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,googleActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}