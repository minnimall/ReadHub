package com.example.lab10mysql_registerlogin

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentAPI {
    @GET("allRegister")
    fun retrieveStudent(): Call<List<LoginClass>>

    @GET("login/{std_id}/{std_password}")
    fun loginStudent(
        @Path("std_id") std_id: String,
        @Path("std_password") std_password: String,
    ): Call<LoginClass>

    @GET("search/{std_id}")
    fun searchStudent(
        @Path("std_id") std_id: String
    ): Call<ProfileClass>

    @FormUrlEncoded
    @POST("insertAccount")
    fun registerStudent(
        @Field("std_id") std_id: String,
        @Field("std_name") std_name: String,
        @Field("std_gender") std_geder: String,
        @Field("std_password") std_password: String,
    ): Call<LoginClass>

    companion object{
        fun create(): StudentAPI {
            val stdClient: StudentAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StudentAPI::class.java)
            return stdClient
        }
    }
}