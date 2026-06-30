package com.example.saneddriverapp.domain.usecase.registration

import android.content.Context
import android.net.Uri
import com.example.saneddriverapp.data.repository.UploadRepository
import javax.inject.Inject

class UploadAttachmentUseCase @Inject constructor(
    private val repository: UploadRepository
) {
    suspend operator fun invoke(
        context: Context,
        requestId: Long,
        uri: Uri,
        attachmentType: String
    ) =
        repository.uploadAttachment(
            context,
            requestId,
            uri,
            attachmentType
        )
}