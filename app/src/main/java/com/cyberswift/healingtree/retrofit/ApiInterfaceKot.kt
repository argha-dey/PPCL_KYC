package com.cyberswift.healingtree.retrofit

import com.cyberswift.healingtree.model.CategoryResponse
import com.cyberswift.healingtree.model.UserForgotPasswordResponseModel
import com.cyberswift.healingtree.model.UserLoginResponseModel
import com.cyberswift.healingtree.model.UserRegisterResponseModel
import com.cyberswift.healingtree.utils.Urls
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


public interface ApiInterfaceKot {
    @GET("?json=get_category_index")
    abstract fun getCategoryDetails(): Call<CategoryResponse>

    @POST(Urls.LOGIN_URL)
    abstract fun loginApiCall(@Body body: Map<String, String>): Call<UserLoginResponseModel>

    @POST(Urls.REGISTRATION_URL)
    abstract fun registrationApiCall(@Body body: Map<String, String>): Call<UserRegisterResponseModel>

    @POST(Urls.FORGOT_PASSWORD_URL)
    abstract fun forgotPasswordApiCall(@Body body: Map<String, String>): Call<UserForgotPasswordResponseModel>

    companion object Factory {
        fun create(): ApiInterfaceKot {
            val retrofit = Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterfaceKot::class.java)
        }
    }
}