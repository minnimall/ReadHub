package com.example.readhub

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApi {
    @GET("/login/{user_name}/{password}")
    fun loginUser(
        @Path("user_name") user_name:String,
        @Path("password") password:String
    ): Call<LoginClass>

    @GET("search/{user_name}")
    fun searchUser(
        @Path("user_name") user_name: String
    ): Call<user>

    @FormUrlEncoded
    @POST("/insertUser")
    fun registerUser(
        @Field("user_name") user_name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        ): Call<LoginClass>

    @FormUrlEncoded
    @POST("/addUserInterested")
    fun addUserInterested(
        @Field("itemselected") itemselected:String,
    ): Call<LoginClass>

    // แสดงหนังสือทั้งหมด ใช้ได้แล้ว
    @GET("allBook")
    fun allBook(): Call<List<Book>>

    // find book by name ใช้ได้แล้ว
    @GET("/book/{book_name}")
    fun searchBook(
        @Path("book_name") book_name: String
    ): Call<List<Book>>

    // เพิ่มจำนวนเข้ารับชม ใช้ได้แล้ว
    @POST("/updateNumOfRead/{book_id}")
        fun updateNumOfRead(
        @Path("book_id") bookId: Int
    ): Call<Void>

    // เพิ่มฟังก์ชันสำหรับบันทึกหนังสือลงใน Bookshelf ยังใช้ไม่ได้
    @POST("/addToBookshelf/{book_id}/{user_name}")
    fun addToBookshelf(
        @Path("book_id") bookId: Int,
        @Path("user_name") userName: String?
    ): Call<Void>


    @GET("MyBookShelf/{user_name}")
    fun MyBookShelf(
        @Path("user_name") userName: String?
    ): Call<List<Book>>

    @GET("/SearchMyBookShelf/{user_name}/{book_name}")
    fun SearchOnBookShelf(
        @Path("user_name") userName: String?,
        @Path("book_name") bookName: String
    ): Call<List<Book>>

    @DELETE("/deleteBookShelf/{user_name}/{book_id}")
    fun DeleteBookOnShelf(
        @Path("user_name") userName: String?,
        @Path("book_id") bookId: Int
    ): Call<Void>

    @Multipart
    @POST("/insertUpload")
    fun uploadFile(
        @Part bookPart: MultipartBody.Part,
        @Part("book_name") bookName: RequestBody,
        @Part("description") description: RequestBody,
        @Part("writer_name") writer_name: RequestBody,
        @Part("pub_name") pub_name: RequestBody,
        @Part("catagory") catagory: RequestBody,
        ): Call<Book>

    @FormUrlEncoded
    @PUT("/userChangepass/{email}")
    fun ResetPasswordUser(
        @Path("email") email: String,
        @Field("password") password: String,
    ): Call<user>

//    @FormUrlEncoded
//    @POST("/insertBook")
//    fun insertBook(
//        @Field("book_name") book_name:String,
//        @Field("description") description:String,
//
//        ): Call<LoginClass>

    companion object{
        fun create(): UserApi{
            val stuClient: UserApi = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApi::class.java)
            return  stuClient
        }
    }
}
