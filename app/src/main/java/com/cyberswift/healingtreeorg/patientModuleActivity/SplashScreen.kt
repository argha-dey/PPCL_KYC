package com.cyberswift.healingtreeorg.patientModuleActivity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import com.cyberswift.healingtreeorg.R
import com.cyberswift.healingtreeorg.homeCareAttendanceModuleActivity.AssignedTaskActivity
import com.cyberswift.healingtreeorg.utils.Constants
import com.cyberswift.healingtreeorg.utils.Prefs

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

    @RequiresApi(Build.VERSION_CODES.O)
    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            if (prefs!!.loginStatus){
                if(prefs!!.userRoleName.equals(Constants.ROLE_PATIENT)){
                    gotoHomePage()
                }
                else if(prefs!!.userRoleName.equals(Constants.ROLE_NURSE)){
                    goToAttendancePage()
                }
                else if(prefs!!.userRoleName.equals(Constants.ROLE_MEDICINE_DELIVERY)){
                    goToAttendancePage()
                }
        }
            else{
              //  Utils.printKeyHash(this@SplashScreen)
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
    private fun goToAttendancePage(){
        val intentHome = Intent(applicationContext, AssignedTaskActivity::class.java)
        startActivity(intentHome)
        finish()
    }
}
