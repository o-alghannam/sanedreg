package com.example.saneddriverapp.data.repository

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.example.saneddriverapp.data.remote.api.UploadApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class UploadRepository @Inject constructor(
    private val api: UploadApi
) {

    suspend fun uploadAttachment(
        context: Context,
        requestId: Long,
        uri: Uri,
        attachmentType: String
    ) {

        val mimeType = getMimeType(context, uri)

        val inputStream =
            context.contentResolver.openInputStream(uri)
                ?: throw IllegalStateException("Cannot open file")

        val tempFile =
            File.createTempFile(
                "upload",
                null,
                context.cacheDir
            )

        FileOutputStream(tempFile).use { output ->
            inputStream.copyTo(output)
        }

        val fileBody =
            tempFile.asRequestBody(
                mimeType.toMediaTypeOrNull()
            )

        val filePart =
            MultipartBody.Part.createFormData(
                "file",
                tempFile.name,
                fileBody
            )

        api.uploadAttachment(
            requestId.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull()),
            attachmentType
                .toRequestBody("text/plain".toMediaTypeOrNull()),
            filePart
        )
    }

    private fun getMimeType(
        context: Context,
        uri: Uri
    ): String {

        val type =
            context.contentResolver.getType(uri)

        if (type != null && type != "image/*") {
            return type
        }

        val extension =
            MimeTypeMap.getFileExtensionFromUrl(
                uri.toString()
            )

        return MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(
                extension.lowercase()
            ) ?: "image/jpeg"
    }
}