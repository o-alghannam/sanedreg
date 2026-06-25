package com.example.saneddriverapp.data.remote.dto.response

import android.net.Uri

data class DocumentsDto(
    val personalPictureUri: Uri?,
    val iqamaUri: Uri?,
    val licenseUri: Uri?,
    val registrationUri: Uri?,
    val additionalFile1Uri: Uri?,
    val additionalFile2Uri: Uri?
)