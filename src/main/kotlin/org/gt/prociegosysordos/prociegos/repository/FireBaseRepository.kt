package org.gt.prociegosysordos.prociegos.repository
import com.google.firebase.cloud.FirestoreClient
import org.gt.prociegosysordos.prociegos.model.LoginSchema.*
import org.gt.prociegosysordos.prociegos.model.VideosSchema
import org.gt.prociegosysordos.prociegos.model.VideosSchema.*
import org.springframework.stereotype.Service

@Service
class FireBaseRepository {
    fun login(login: Login): LoginResponse {
        val storage = FirestoreClient.getFirestore()
        val reference = storage.collection("user").whereEqualTo("email",login.userName)
        val document = reference.get().get()

        if (document.isEmpty) return LoginResponse(null, null, null, null,false)

        val response = document.first().toObject(DocLogin::class.java)
        return if (response.password == login.password) {
            LoginResponse(response.name, response.lastname, response.username, queryVideos(response.accountType?.apply { this }?:"0"), true)
        }else
            LoginResponse(null, null, null, null,false)
    }


    fun getVideo(videos: String): VideoExposed {
        val storage = FirestoreClient.getFirestore()
        val reference = storage.collection("videos").whereEqualTo("id", videos)
        val document = reference.get().get()

        if (document.isEmpty) return VideoExposed(null, null, null, null, false)

        val response = document.first().toObject(VideoResponse::class.java)
        //return if (type == response.type)
            return VideoExposed(response.title, response.description, response.url, response.category, true)
        //else
          //  VideoExposed(null, null, null, null, false)
    }

    fun queryVideos(type: String): MutableList<Video> {
        val storage = FirestoreClient.getFirestore()
        val reference = storage.collection("videos").whereEqualTo("type", type)
        val document = reference.get().get()
        if (document.isEmpty) return mutableListOf()
        val response = mutableListOf<Video>()
        for (doc in document) {
            val ans = doc.toObject(VideoResponse::class.java)
            response.add(Video(ans.title!!, ans.id!!))
        }
        return response
    }
}