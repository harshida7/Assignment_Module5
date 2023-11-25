package com.example.uploadprofile_ex

import android.widget.ImageView
import android.widget.TextView
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadService {
    @Multipart
    @POST("uploaddata.php")
    suspend fun uploadImage(
        @Part image1: MultipartBody.Part,
        @Part("emp_name") emp_name: RequestBody?,
        @Part("emp_mobile") emp_mobile: RequestBody?,
        @Part("emp_email") emp_email: RequestBody?,
        @Part("emp_pass") emp_pass: RequestBody?,
    ): ResponseBody


    @FormUrlEncoded
    @POST("login.php")
    fun logindata(

        @Field("emp_email") emp_email: String?,
        @Field("emp_pass") emp_pass: String?,

        ): Call<Model>


    @FormUrlEncoded
    @POST("profile.php")
    fun viewData(

        @Field("emp_email") emp_email: String?,
        @Field("emp_pass") emp_pass: String?,

        ): Call<Model>



}