package org.d3if3101.assesment3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3101.assesment3.model.Mobil
import org.d3if3101.assesment3.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface HotWheelsApiService {
    @GET("api_alfonsus.php")
    suspend fun getHotWheel(
        @Header("Authorization") userId: String
    ): List<Mobil>

    @Multipart
    @POST("api_alfonsus.php")
    suspend fun postHotWheel(
        @Header("Authorization") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("edisi") edisi: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("api_alfonsus.php")
    suspend fun delete(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object HotWheelApi{
    val service: HotWheelsApiService by lazy {
        retrofit.create(HotWheelsApiService::class.java)
    }

    fun getHotWheelUrl(imageId: String): String{
        return "${BASE_URL}image.php?id=$imageId"
    }


}

enum class ApiStatus{LOADING, SUCCESS, FAILED}