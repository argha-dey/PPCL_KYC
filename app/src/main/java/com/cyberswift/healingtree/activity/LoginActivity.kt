package com.cyberswift.healingtree.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.cyberswift.healingtree.R
import com.cyberswift.healingtree.model.UserLoginResponseModel
import com.cyberswift.healingtree.retrofit.ApiInterfaceKot
import com.cyberswift.healingtree.utils.Prefs
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class LoginActivity : Activity() {
    private var prefs: Prefs? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
    }

    private fun initViews() {
        prefs = Prefs(this@LoginActivity)
        val et_username = findViewById<EditText>(R.id.et_username) as EditText
        val et_password = findViewById<EditText>(R.id.et_password) as EditText
        val  iv_gmail_click = findViewById<ImageView>(R.id.iv_gmail_click) as ImageView
        val  iv_facebook_click = findViewById<ImageView>(R.id.iv_facebook_click) as ImageView
        iv_gmail_click.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@LoginActivity, "Available Shortly", Toast.LENGTH_LONG).show()

            }
        })
        iv_facebook_click.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@LoginActivity, "Available Shortly", Toast.LENGTH_LONG).show()

            }
        })

        val btnLoginClick = findViewById<Button>(R.id.btn_login_click) as Button
        val registerHereClick = findViewById<TextView>(R.id.tv_register_here) as TextView
        registerHereClick.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                goToRegisterPage()

            }
        })
        btnLoginClick.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (et_username.text.toString().trim().isEmpty()) {
                    et_username.error = "Required"
                    Toast.makeText(applicationContext, "User ID Required ", Toast.LENGTH_SHORT).show()
                }

                else if (et_password.text.toString().trim().isEmpty()) {
                    et_password.error = "Required"
                    Toast.makeText(applicationContext, "Password Required ", Toast.LENGTH_SHORT).show()
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
     requestBody.put("password",et_password.text.toString())
        val apiService = ApiInterfaceKot.create()
        val call = apiService.loginApiCall(requestBody)
        call.enqueue(object : Callback<UserLoginResponseModel> {
            override fun onResponse(call: Call<UserLoginResponseModel>, response: retrofit2.Response<UserLoginResponseModel>?) {
                if (response != null) {
                    if (response.body()!!.status) {
                        if (pDialog != null && pDialog!!.isShowing()) {
                            pDialog.dismiss()
                        }
                        prefs?.setUserID(response.body()!!.data.get(0).id)
                        prefs?.setUserFirstname(response.body()!!.data.get(0).firstName)
                        prefs?.setUserLastname(response.body()!!.data.get(0).lastName)
                        prefs?.setUserEmailId(response.body()!!.data.get(0).email)
                        prefs?.setUserPhoneNumber(response.body()!!.data.get(0).phone1)
                        prefs?.setUserAddress(response.body()!!.data.get(0).address)
                        prefs?.setLoginStatus(true)
                        goToHomePage()
                    }
                    else{
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.dismiss()
                        }
                        Toast.makeText(applicationContext, "  Login fail! ", Toast.LENGTH_SHORT).show()
                    }
                    } else
                        Toast.makeText(applicationContext, " Login fail! ", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<UserLoginResponseModel>, t: Throwable) {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss()
                }
            }
        })
    }
    lateinit var pDialog: ProgressDialog
    fun DisplayProgressDialog() {

        pDialog = ProgressDialog(this@LoginActivity)
        pDialog!!.setMessage("Loading..")
        pDialog!!.setCancelable(false)
        pDialog!!.isIndeterminate = false
        pDialog!!.show()
    }
    private fun goToHomePage(){
        val intentHome = Intent(applicationContext, HomeActivity::class.java)
        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentHome)
    }
    private fun goToRegisterPage(){
        val intentHome = Intent(applicationContext, RegistrationActivity::class.java)
        startActivity(intentHome)
    }
}
