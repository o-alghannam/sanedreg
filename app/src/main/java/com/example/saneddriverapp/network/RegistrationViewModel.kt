package com.example.saneddriverapp.network
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import com.example.saneddriverapp.RegistrationRequest
import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import android.webkit.MimeTypeMap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.io.FileOutputStream
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
class RegistrationViewModel : ViewModel() {


    var fullName by mutableStateOf("")
    var gender by mutableStateOf("")
    var expiryDate by mutableStateOf("")
    var dateBirth by mutableStateOf("")
    var nationality by mutableStateOf("")
    var city by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    class VehicleFormState {

        var vehicleType by mutableStateOf("")
        var vehicleModel by mutableStateOf("")
        var vehicleName by mutableStateOf("")
        var vehicleSequenceNumber by mutableStateOf("")
        var vehicleNumberPlateEn by mutableStateOf("")
        var vehicleNumberPlateAr by mutableStateOf("")
        var vehicleRegistrationExpiryDate by mutableStateOf("")
        var driverLicenseExpiryDate by mutableStateOf("")
    }
    val vehicleForm = VehicleFormState()
    suspend fun uploadAttachment(
        context: Context,
        requestId: Long,
        uri: Uri,
        attachmentType: String
    ) {
        try {

            val mimeType = getMimeType(context, uri)

            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw IllegalStateException("Cannot open file")

            val tempFile = File.createTempFile("upload", null, context.cacheDir)

            FileOutputStream(tempFile).use { output ->
                inputStream.copyTo(output)
            }

            val requestFile = tempFile
                .asRequestBody(mimeType.toMediaTypeOrNull())

            val multipartFile = MultipartBody.Part.createFormData(
                "file",
                tempFile.name,
                requestFile
            )

            val response = RetrofitInstance.api.uploadAttachment(
                requestId = requestId.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull()),

                attachmentType = attachmentType
                    .toRequestBody("text/plain".toMediaTypeOrNull()),

                file = multipartFile
            )

            Log.d("TEST", "HTTP ${response.code()}")

            if (!response.isSuccessful) {
                Log.e("TEST", response.errorBody()?.string() ?: "No error body")
            }

        } catch (e: Exception) {
            Log.e("UPLOAD", "Upload failed", e)
            throw e
        }
    }
    var personalInfo: PersonalInfoDto? = null

    var vehicleInfo: VehicleInfoDto? = null


    suspend fun submitInfo(
        request: SubmitInfoRequest
    ) {
        RetrofitInstance.api.submitInfo(request)
    }

    suspend fun finalizeApplication(
        requestId: Long
    ) {
        try {

            RetrofitInstance.api.finalizeApplication(requestId)

            Log.d(
                "API",
                "Finalize Success"
            )

        } catch (e: HttpException) {
            Log.e("API", "HTTP ${e.code()}")
            Log.e("API", e.response()?.errorBody()?.string() ?: "No error body")
        }
    }
    fun getMimeType(context: Context, uri: Uri): String {
        val type = context.contentResolver.getType(uri)
        if (type != null && type != "image/*") return type

        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())

        return MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(extension.lowercase())
            ?: "image/jpeg"
    }
}