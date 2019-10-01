package com.cyberswift.healingtree.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.cyberswift.healingtree.R
import com.cyberswift.healingtree.model.UserRegisterResponseModel
import com.cyberswift.healingtree.retrofit.ApiInterfaceKot
import com.cyberswift.healingtree.utils.Prefs
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class RegistrationActivity : Activity() {
    val stateNameArray = arrayOf(
        "Select State",
        "Gujarat"
    )
    val cityNameArray = arrayOf(
        "Select City",
        "Anand",
        "Vadodara",
        "Bhavnagar",
        "Bharuch",
        "Gandhidham",
        "Jamnagar",
        "Junagadh",
        "Rajkot",
        "Morvi",
        "Navsari",
        "Nadiad",
        "Surendranagar",
        "Porbandar"
    )

    var statename: String = ""
    var  statePosition: Int = 0
    var cityname: String = ""
    var  cityPosition: Int = 0

    private var mPrefs: Prefs? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initViews()
    }

    private fun initViews() {
        mPrefs = Prefs(this@RegistrationActivity)
        val tv_first_name = findViewById<EditText>(R.id.tv_first_name) as EditText
        val tv_last_name = findViewById<EditText>(R.id.tv_last_name) as EditText
        val tv_hospital_id = findViewById<EditText>(R.id.tv_hospital_id) as EditText
        val tv_email = findViewById<EditText>(R.id.tv_email) as EditText
        val tv_age = findViewById<EditText>(R.id.tv_age) as EditText
        val tv_Zip_code = findViewById<EditText>(R.id.tv_Zip_code) as EditText
        val tv_phone_one = findViewById<EditText>(R.id.tv_phone_one) as EditText
        val tv_phone_two = findViewById<EditText>(R.id.tv_phone_two) as EditText

        val tv_address = findViewById<EditText>(R.id.tv_address) as EditText
        val tv_password = findViewById<EditText>(R.id.tv_password) as EditText
        val tv_confirm_password = findViewById<EditText>(R.id.tv_confirm_password) as EditText
        val tv_cancel = findViewById<TextView>(R.id.tv_cancel) as TextView

        val rg_hospital_id_yes_no = findViewById<RadioGroup>(R.id.rg_hospital_id_yes_no) as RadioGroup
        val rb_no = findViewById<RadioButton>(R.id.rb_no) as RadioButton
        val rb_yes = findViewById<RadioButton>(R.id.rb_yes) as RadioButton
        val rl_hospital_id = findViewById<RelativeLayout>(R.id.rl_hospital_id) as RelativeLayout


        tv_cancel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                goToHomePage()

            }
        })


        val backButtonClick = findViewById<TextView>(R.id.tv_back_button) as TextView


        backButtonClick.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                goToHomePage()

            }
        })

        val  tv_save  = findViewById<TextView>(R.id.tv_save) as TextView
        tv_save.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                registrationApiCall()
            }
        })


        val state = findViewById<Spinner>(R.id.tv_state) as Spinner
        if (state != null) {
            val arrayAdapter = ArrayAdapter(this, R.layout.spinner_row, stateNameArray)
            state.adapter = arrayAdapter

            state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    statePosition = position
                    statename = stateNameArray[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        val city = findViewById<Spinner>(R.id.tv_city) as Spinner
        if (city != null) {
            val arrayAdapter = ArrayAdapter(this,  R.layout.spinner_row, cityNameArray)
            city.adapter = arrayAdapter

            city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    cityPosition = position
                    cityname = cityNameArray[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }
    }


    private fun goToHomePage() {
        val intentBack = Intent(applicationContext, LoginActivity::class.java)
        intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intentBack.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentBack)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rb_no ->
                    if (checked) {
                        rl_hospital_id.visibility = View.GONE
                    }
                R.id.rb_yes ->
                    if (checked) {
                        rl_hospital_id.visibility = View.VISIBLE
                    }
            }
        }
    }


    private fun registrationApiCall() {

        if (tv_first_name.text.toString().trim().isEmpty()) {
            tv_first_name.error = "Required"
            Toast.makeText(applicationContext, "First Name Required ", Toast.LENGTH_SHORT).show()
        }
        else if (tv_last_name.text.toString().trim().isEmpty()) {
            tv_last_name.error = "Required"
            Toast.makeText(applicationContext, "Last Name Required ", Toast.LENGTH_SHORT).show()
        }
        else if (tv_age.text.toString().trim().isEmpty()) {
            tv_age.error = "Required"
            Toast.makeText(applicationContext, "Age Required ", Toast.LENGTH_SHORT).show()
        }
        else if (tv_Zip_code.text.toString().trim().isEmpty()) {
            tv_Zip_code.error = "Required"
            Toast.makeText(applicationContext, "Zip Code Required ", Toast.LENGTH_SHORT).show()
        }
        else if (tv_phone_one.text.toString().trim().isEmpty()) {
            tv_phone_one.error = "Required"
            Toast.makeText(applicationContext, "Phone Number Required ", Toast.LENGTH_SHORT).show()
        }
        else if (statePosition==0) {
            Toast.makeText(applicationContext, "Select State ", Toast.LENGTH_SHORT).show()
        }
        else if (cityPosition==0) {
            Toast.makeText(applicationContext, "City Required ", Toast.LENGTH_SHORT).show()
        }
        else if (tv_address.text.toString().trim().isEmpty()) {
            tv_address.error = "Required"
            Toast.makeText(applicationContext, "Address Required ", Toast.LENGTH_SHORT).show()
        }
        else if (rl_hospital_id.visibility == View.VISIBLE  && tv_hospital_id.text.toString().trim().isEmpty()) {
            tv_hospital_id.error = "Required"
            Toast.makeText(applicationContext, "Hospital Id Required ", Toast.LENGTH_SHORT).show()
        }
        else if (tv_password.text.toString().trim().isEmpty()) {
            tv_password.error = "Required"
            Toast.makeText(applicationContext, "Password Required ", Toast.LENGTH_SHORT).show()
        }
        else if (tv_confirm_password.text.toString().trim().isEmpty()) {
            tv_confirm_password.error = "Required"
            Toast.makeText(applicationContext, "Confirm Password Required ", Toast.LENGTH_SHORT).show()
        }
        else{
            DisplayProgressDialog()
            val requestBody = HashMap<String, String>()
            requestBody.put("first_name", tv_first_name.text.toString())
            requestBody.put("last_name", tv_last_name.text.toString())
            requestBody.put("user_id", tv_phone_one.text.toString())
            requestBody.put("email",tv_email.text.toString())
            requestBody.put("age",tv_age.text.toString())
            requestBody.put("zip_code", tv_Zip_code.text.toString())
            requestBody.put("phone1", tv_phone_one.text.toString())
            requestBody.put("phone2", tv_phone_two.text.toString())
            requestBody.put("state", statename)
            requestBody.put("city",cityname)
            requestBody.put("address",tv_address.text.toString())
            requestBody.put("hospital_id",tv_hospital_id.text.toString())
            requestBody.put("password",tv_password.text.toString())
            requestBody.put("confirm_password", tv_confirm_password.text.toString())

            val apiService = ApiInterfaceKot.create()
            val call = apiService.registrationApiCall(requestBody)
            call.enqueue(object : Callback<UserRegisterResponseModel> {
                override fun onResponse(
                    call: Call<UserRegisterResponseModel>, response: retrofit2.Response<UserRegisterResponseModel>?
                ) {
                    if (response != null) {
                        if (response.body()!!.isStatus) {

                        if (pDialog != null && pDialog!!.isShowing()) {
                            pDialog.dismiss()
                        }

                        Toast.makeText(applicationContext, "Registration Successfully Done ", Toast.LENGTH_LONG).show()
                            goToHomePage()
                    }
                        else{
                            if (pDialog != null && pDialog.isShowing()) {
                                pDialog.dismiss()
                            }
                            Toast.makeText(applicationContext, " Registration fail! ", Toast.LENGTH_SHORT).show()
                        }
                }else
                        Toast.makeText(applicationContext, " Registration fail! ", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UserRegisterResponseModel>, t: Throwable) {
                    //                Log.e(TAG, t.toString());
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.dismiss()
                    }
                }
            })

        }

    }

    lateinit var pDialog: ProgressDialog
    fun DisplayProgressDialog() {
        pDialog = ProgressDialog(this@RegistrationActivity)
        pDialog!!.setMessage("Loading..")
        pDialog!!.setCancelable(false)
        pDialog!!.isIndeterminate = false
        pDialog!!.show()
    }

    override fun onBackPressed() {

    }
}


