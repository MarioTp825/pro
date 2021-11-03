package org.gt.prociegosysordos.prociegos.model

import org.springframework.boot.autoconfigure.domain.EntityScan
import javax.persistence.Id

sealed class VideosSchema {
    @EntityScan("videos")
    data class VideoResponse(
        @Id val id: String? = "",
        val category: String? = "",
        val description: String? = "",
        val title: String? = "",
        val type: String? = "",
        val url: String? = "",
        val visible: Boolean? = false
    )

    data class Video (
        val title: String,
        val id: String
    )

    data class  VideoExposed(
        val title: String?,
        val description: String?,
        val url: String?,
        val category: String?,
        val successful: Boolean
    )
}
