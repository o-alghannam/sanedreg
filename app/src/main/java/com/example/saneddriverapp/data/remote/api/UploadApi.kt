package com.example.saneddriverapp.data.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadApi {

    @Multipart
    @POST("api/v3/drivers-management/account/upload-attachment")
    suspend fun uploadAttachment(
        @Part("requestId") requestId: RequestBody,
        @Part("attachmentType") attachmentType: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>
}