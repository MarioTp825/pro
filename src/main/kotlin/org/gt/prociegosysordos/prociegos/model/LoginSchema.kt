package org.gt.prociegosysordos.prociegos.model

import com.google.gson.annotations.SerializedName
import org.springframework.boot.autoconfigure.domain.EntityScan
import javax.persistence.Entity
import javax.persistence.Id

sealed class LoginSchema {
    data class LoginResponse(
        val name: String?,
        val lastname: String?,
        val email: String?,
        val videos: MutableList<VideosSchema.Video>?,
        val isSuccessful: Boolean
    )

    @EntityScan("user")
    data class DocLogin(
        @Id val id: String? = "",
        val name: String? = "",
        @SerializedName("email") val username: String? = "",
        val lastname: String? = "",
        val password: String? = "",
        val accountType: String? = "",
        @SerializedName("passwordChangeRequired") val isPasswordChangeRequired: Boolean? = false,
        val status: Boolean? = false,
    )

    data class Login (
        val userName: String?,
        val password: String?
    )
}
