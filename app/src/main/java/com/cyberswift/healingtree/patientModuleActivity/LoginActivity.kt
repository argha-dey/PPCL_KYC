package com.cyberswift.healingtree.patientModuleActivity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.cyberswift.healingtree.AppController.TAG
import com.cyberswift.healingtree.homeCareAttendanceModuleActivity.AssignedTaskActivity
import com.cyberswift.healingtree.R
import com.cyberswift.healingtree.model.UserLoginResponseModel
import com.cyberswift.healingtree.retrofit.ApiInterfaceKot
import com.cyberswift.healingtree.utils.Constants
import com.cyberswift.healingtree.utils.Prefs
import com.cyberswift.healingtree.utils.Utils
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class LoginActivity : FragmentActivity(), GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("HEALING TREE ", "On Connection Failed:" + connectionResult)
        Toast.makeText(this, "Google sign Connection in failed!", Toast.LENGTH_LONG).show()
    }
    private var prefs: Prefs? = null
    private  var token: String? = ""
    private var iv_gmail_click: ImageView? = null
    private var callbackManager: CallbackManager? = null

  //  private var own_facebook_login_button: LoginButton? = null

    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        getToken()
        configureGoogleSignIn()
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        iv_gmail_click?.setOnClickListener(View.OnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)



        if (requestCode == RC_SIGN_IN) {
            DisplayProgressDialog()
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }

    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            if (pDialog != null && pDialog!!.isShowing()) {
                pDialog.dismiss()
            }
            val user = result.signInAccount
            callWebServiceWhenLoginFromGmail(user!!.displayName,user!!.email,user!!.id)
        }
        else{
            if (pDialog != null && pDialog!!.isShowing()) {
                pDialog.dismiss()
            }
            Toast.makeText(this, "Google sign in failed!", Toast.LENGTH_LONG).show()
        }
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
        prefs = Prefs(this@LoginActivity)
        val et_username = findViewById<View>(R.id.et_username) as EditText
        val et_password = findViewById<EditText>(R.id.et_password) as EditText
        iv_gmail_click = findViewById(R.id.iv_gmail_click)
      //  own_facebook_login_button = findViewById(R.id.own_facebook_login_button)

        val  iv_facebook_click = findViewById<ImageView>(R.id.iv_facebook_click) as ImageView
        val  ll_forgot_password = findViewById<View>(R.id.ll_forgot_password) as LinearLayout

        et_password.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (et_username.text.toString().trim().isEmpty()) {
                    et_username.error = "Required"
                    Toast.makeText(applicationContext, "User ID Required ", Toast.LENGTH_SHORT).show()
                }

                else if (et_password.text.toString().trim().isEmpty()) {
                    et_password.error = "Required"
                    Toast.makeText(applicationContext, "Password Required ", Toast.LENGTH_SHORT).show()
                }
                else{

                    if(isOnline(this@LoginActivity) ){
                        DisplayProgressDialog()
                        callWebService()
                    }
                    else{
                        Toast.makeText(applicationContext, "Please check your Internet connection!", Toast.LENGTH_SHORT).show()
                    }
                }

                return@setOnEditorActionListener true
            }
            false
        }





        ll_forgot_password.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                goToForgotPasswordPAge()

            }
        })


        iv_facebook_click.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                facebookLogin()
              //  Toast.makeText(this@LoginActivity, "Available Shortly", Toast.LENGTH_LONG).show()

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
                    if(isOnline(this@LoginActivity) ){
                        DisplayProgressDialog()
                        callWebService()
                    }
                    else{
                        Toast.makeText(applicationContext, "Please check your Internet connection!", Toast.LENGTH_SHORT).show()
                    }
                }
            }})

    }

    private fun facebookLogin(){
            // Facebook Login
        //fb login
           callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this@LoginActivity, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                                try {
                                    //here is the data that you want
                                    Log.d("FBLOGIN_JSON_RES", `object`.toString())

                                /*    if (`object`.has("id")) {
                                        Toast.makeText(applicationContext, "Facebook Id "+`object`.optString("id"), Toast.LENGTH_SHORT).show()
                                    }*/

                                    callWebServiceWhenLoginFromFaceBook(`object`.optString("name"),`object`.optString("email"),`object`.optString("id"))

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                  //  dismissDialogLogin()
                                }
                            }

                            val parameters = Bundle()
                            parameters.putString("fields", "name,email,id,picture.type(large)")
                            request.parameters = parameters
                            request.executeAsync()
                        }
                    }
                    override fun onCancel() {
                        Toast.makeText(applicationContext, "Facebook sign in Cancel!", Toast.LENGTH_LONG).show()

                    }

                    override fun onError(error: FacebookException) {

                        Toast.makeText(applicationContext, "Facebook sign in failed!", Toast.LENGTH_LONG).show()

                    }
                })


    }


    private   fun callWebService() {
        val requestBody = HashMap<String, String>()
        requestBody.put("user_id", et_username.text.toString())
        requestBody.put("password",et_password.text.toString())
        requestBody.put("deviceToken",token.toString())
        requestBody.put("facebook_id","")
        requestBody.put("gmail_id","")
        requestBody.put("deviceId",Utils.getMobileDeviceID(this@LoginActivity))
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
                        prefs?.setUserRoleName(response.body()!!.data.get(0).role_name)
                        prefs?.setLoginStatus(true)

                        if(response.body()!!.data.get(0).role_name.equals(Constants.ROLE_PATIENT)){
                            Toast.makeText(applicationContext, " Login Successfully Done! ", Toast.LENGTH_SHORT).show()
                            goToHomePage()
                            //   goToAttendancePage()
                        }
                        else if(response.body()!!.data.get(0).role_name.equals(Constants.ROLE_NURSE)){
                            Toast.makeText(applicationContext, " Login Successfully Done! ", Toast.LENGTH_SHORT).show()
                            goToAttendancePage()
                        }
                        else if(response.body()!!.data.get(0).role_name.equals(Constants.ROLE_PHYSIOTHERAPIST)){
                            Toast.makeText(applicationContext, " Login Successfully Done! ", Toast.LENGTH_SHORT).show()
                            goToAttendancePage()
                        }
                        else if(response.body()!!.data.get(0).role_name.equals(Constants.ROLE_ATTENDANT)){
                            Toast.makeText(applicationContext, " Login Successfully Done! ", Toast.LENGTH_SHORT).show()
                            goToAttendancePage()
                        }
                        else if(response.body()!!.data.get(0).role_name.equals(Constants.ROLE_MEDICINE_DELIVERY)){
                            Toast.makeText(applicationContext, " Login Successfully Done! ", Toast.LENGTH_SHORT).show()
                            goToAttendancePage()
                        }
                        else{
                            Toast.makeText(applicationContext, "  Your Role are not applicable for application use! ", Toast.LENGTH_LONG).show()
                        }

                    }
                    else{
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.dismiss()
                            //   goToAttendancePage()
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

    private   fun callWebServiceWhenLoginFromGmail(displayName: String?, email: String?, id: String?) {
        DisplayProgressDialog()
        val requestBody = HashMap<String, String>()
        requestBody.put("user_id","")
        requestBody.put("password","")
        requestBody.put("deviceToken",token.toString())
        requestBody.put("facebook_id","")
        requestBody.put("gmail_id", id.toString())
        requestBody.put("deviceId",Utils.getMobileDeviceID(this@LoginActivity))
        val apiService = ApiInterfaceKot.create()
        val call = apiService.loginApiCall(requestBody)
        call.enqueue(object : Callback<UserLoginResponseModel> {
            override fun onResponse(call: Call<UserLoginResponseModel>, response: retrofit2.Response<UserLoginResponseModel>?) {
                if (response != null) {
                    if (response.body()!!.status) {
                        if (pDialog != null && pDialog!!.isShowing()) {
                            pDialog.dismiss()
                        }
                        val string: String? = displayName
                        val displayNameArray: List<String> = string!!.split(" ")
                        prefs?.setUserID(response.body()!!.data.get(0).id)
                        prefs?.setUserFirstname(displayNameArray[0])
                        prefs?.setUserLastname(displayNameArray[1])
                        prefs?.setUserEmailId(email)
                        prefs?.setUserPhoneNumber(response.body()!!.data.get(0).phone1)
                        prefs?.setUserAddress(response.body()!!.data.get(0).address)
                        prefs?.setUserRoleName(response.body()!!.data.get(0).role_name)
                        prefs?.setLoginStatus(true)
                        Toast.makeText(applicationContext, " Login Successfully Done! ", Toast.LENGTH_SHORT).show()
                        goToHomePage()
                    }
                    else{
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.dismiss()
                            //   goToAttendancePage()
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
    private   fun callWebServiceWhenLoginFromFaceBook(displayName: String?, email: String?, uid: String) {
        DisplayProgressDialog()
        val requestBody = HashMap<String, String>()
        requestBody.put("user_id","")
        requestBody.put("password","")
        requestBody.put("deviceToken",token.toString())
        requestBody.put("facebook_id",uid)
        requestBody.put("gmail_id","")
        requestBody.put("deviceId",Utils.getMobileDeviceID(this@LoginActivity))
        val apiService = ApiInterfaceKot.create()
        val call = apiService.loginApiCall(requestBody)
        call.enqueue(object : Callback<UserLoginResponseModel> {
            override fun onResponse(call: Call<UserLoginResponseModel>, response: retrofit2.Response<UserLoginResponseModel>?) {
                if (response != null) {
                    if (response.body()!!.status) {
                        if (pDialog != null && pDialog!!.isShowing()) {
                            pDialog.dismiss()
                        }
                        val string: String? = displayName
                        val displayNameArray: List<String> = string!!.split(" ")
                        prefs?.setUserID(response.body()!!.data.get(0).id)
                        prefs?.setUserFirstname(displayNameArray[0])
                        prefs?.setUserLastname(displayNameArray[1])
                        prefs?.setUserEmailId(email)
                        prefs?.setUserPhoneNumber(response.body()!!.data.get(0).phone1)
                        prefs?.setUserAddress(response.body()!!.data.get(0).address)
                        prefs?.setUserRoleName(response.body()!!.data.get(0).role_name)
                        prefs?.setLoginStatus(true)
                        Toast.makeText(applicationContext, " Login Successfully Done! ", Toast.LENGTH_SHORT).show()
                        goToHomePage()
                    }
                    else{
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.dismiss()
                            //   goToAttendancePage()
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
        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentHome)
    }

    private fun goToAttendancePage(){
        val intentHome = Intent(applicationContext, AssignedTaskActivity::class.java)
        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentHome)
    }

    private fun goToRegisterPage(){
        val intentHome = Intent(applicationContext, RegistrationActivity::class.java)
        startActivity(intentHome)
    }

    private fun goToForgotPasswordPAge(){
        val intentHome = Intent(applicationContext, ForgotPasswordActivity::class.java)
        startActivity(intentHome)
    }
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}


