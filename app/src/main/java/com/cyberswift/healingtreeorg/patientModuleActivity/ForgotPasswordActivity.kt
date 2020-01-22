package com.cyberswift.healingtreeorg.patientModuleActivity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.cyberswift.healingtreeorg.AppController.TAG
import com.cyberswift.healingtreeorg.R
import com.cyberswift.healingtreeorg.model.UserForgotPasswordResponseModel
import com.cyberswift.healingtreeorg.retrofit.ApiInterfaceKot
import com.cyberswift.healingtreeorg.utils.AlertDialogCallBack
import com.cyberswift.healingtreeorg.utils.Prefs
import com.cyberswift.healingtreeorg.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class ForgotPasswordActivity : Activity() {
    private var prefs: Prefs? = null
    private  var token: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        initViews()
        getToken()
    }

    private fun getToken(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                 token = task.result?.token
              //  Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
    }


    private fun initViews() {
        prefs = Prefs(this@ForgotPasswordActivity)
        val et_username = findViewById<EditText>(R.id.et_username) as EditText
        val  ll_goto_login_page = findViewById<LinearLayout>(R.id.ll_goto_login_page) as LinearLayout

        ll_goto_login_page.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                goToLoginPage()
            }
        })

        val btnLoginClick = findViewById<Button>(R.id.btn_login_click) as Button


        btnLoginClick.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (et_username.text.toString().trim().isEmpty()) {
                    et_username.error = "Required"
                    Toast.makeText(applicationContext, "User ID Required ", Toast.LENGTH_SHORT).show()
                }

                else{
                   DisplayProgressDialog()
                    callWebService()
                }
            }})

    }
 private   fun callWebService() {
     val requestBody = HashMap<String, String>()
     requestBody.put("user_id", et_username.text.toString())
        val apiService = ApiInterfaceKot.create()
        val call = apiService.forgotPasswordApiCall(requestBody)
        call.enqueue(object : Callback<UserForgotPasswordResponseModel> {
            override fun onResponse(call: Call<UserForgotPasswordResponseModel>, response: retrofit2.Response<UserForgotPasswordResponseModel>?) {
                try {
                    if (response?.body() != null) {
                        if (response?.body()!!.status) {
                            if (pDialog != null && pDialog!!.isShowing()) {
                                pDialog.dismiss()
                                Utils.showCallBackMessageWithOk(
                                    this@ForgotPasswordActivity,
                                    response.body()!!.message,
                                    object :
                                        AlertDialogCallBack {
                                        override fun onSubmit() {
                                            goToLoginPage()
                                        }
                                        override fun onCancel() {
                                        }
                                    })
                            }
                        } else {
                            if (pDialog != null && pDialog.isShowing()) {
                                pDialog.dismiss()
                            }
                            Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.dismiss()
                        }
                        Toast.makeText(applicationContext, "Some Authentication Error Occur!", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e: NullPointerException) {
                  //  Toast.makeText(applicationContext,"Some Error Occur in  Forgot Password!", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<UserForgotPasswordResponseModel>, t: Throwable) {
                Toast.makeText(applicationContext, "Forgot Password Not To Be Done! ", Toast.LENGTH_SHORT).show()
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss()
                }
            }
        })
    }
    lateinit var pDialog: ProgressDialog
    fun DisplayProgressDialog() {

        pDialog = ProgressDialog(this@ForgotPasswordActivity)
        pDialog!!.setMessage("Loading..")
        pDialog!!.setCancelable(false)
        pDialog!!.isIndeterminate = false
        pDialog!!.show()
    }
    private fun goToLoginPage(){
        val intentHome = Intent(applicationContext, LoginActivity::class.java)
        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentHome)
    }
}
