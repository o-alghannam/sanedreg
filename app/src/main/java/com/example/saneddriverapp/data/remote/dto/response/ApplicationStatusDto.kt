package com.example.saneddriverapp.data.remote.dto.response

data class ApplicationStatusDto(
    val requestId: Long,
    val createdDate: String,
    val status: String,
    val tawseelStatus: String?,
    val reason: String?,
    val fieldsThatNeedUpdate: List<Int>
)