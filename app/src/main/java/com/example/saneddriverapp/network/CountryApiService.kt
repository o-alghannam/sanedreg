package com.example.saneddriverapp.network
import com.example.saneddriverapp.RegistrationRequest
import com.example.saneddriverapp.network.model.SignUpRequest
import com.example.saneddriverapp.network.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.PUT
import retrofit2.http.Path
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Header
interface CountryApiService {


    @Multipart
    @POST("api/v3/drivers-management/account/upload-attachment")
    suspend fun uploadAttachment(
        @Part("requestId") requestId: RequestBody,
        @Part("attachmentType") attachmentType: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>
    @GET("api/v1/drivers-management-portal/lookups/active-countries")
    suspend fun getCountries(): CountriesResponse

    @POST("api/v3/drivers-management/account/complete-sign-up")
    suspend fun completeSignUp(
        @Body request: CompleteSignUpRequest
    ): CompleteSignUpResponse
    @POST("api/v3/drivers-management/account/sign-up")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse

    @GET("api/v1/drivers-management-portal/lookups/cities")
    suspend fun getCities(
        @Query("countryCode") countryCode: String
    ): CityResponse

    @GET("api/v1/drivers-management-portal/lookups/vehicles-model")
    suspend fun getVehicleModels(): VehicleModelResponse

    @GET("api/v1/drivers-management-portal/lookups/vehicle-types")
    suspend fun getVehicleTypes(): VehicleTypeResponse

    @POST("api/v3/drivers-management/account/submit-info")
    suspend fun submitInfo(
        @Body request: SubmitInfoRequest
    ): SubmitInfoResponse

    @PUT(
        "api/v3/drivers-management/account/submit-registration-request/{requestId}"
    )
    suspend fun submitRegistrationRequest(
        @Path("requestId")
        requestId: Long
    )


    @PUT("api/v3/drivers-management/account/submit-registration-request/{requestId}")
    suspend fun finalizeApplication(
        @Path("requestId") requestId: Long
    )
    @POST("api/v3/drivers-management/account/send-review-otp")
    suspend fun sendReviewOtp(
        @Body request: SendReviewOtpRequest
    ): Response<SendReviewOtpResponse>

    @POST("api/v3/drivers-management/account/verify-review-otp")
    suspend fun verifyReviewOtp(
        @Body request: VerifyReviewOtpRequest
    ): Response<VerifyReviewOtpResponse>
}
