package com.cyberswift.healingtree.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.cyberswift.healingtree.R
import com.cyberswift.healingtree.utils.Prefs

class SplashScreen : Activity() {
    var SPLASH_TIME_OUT: Long  = 3000
    private var mDelayHandler: Handler? = null
    private var prefs: Prefs? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        prefs = Prefs(this@SplashScreen)
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_TIME_OUT)

    }

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            if (prefs!!.loginStatus){
                gotoHomePage()
        }
            else{
                gotoLoginPage()
            }
        }
    }
    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

    private fun gotoLoginPage() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun gotoHomePage() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
