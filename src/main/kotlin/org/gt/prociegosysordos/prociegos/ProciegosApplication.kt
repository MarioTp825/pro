package org.gt.prociegosysordos.prociegos

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import java.io.FileInputStream
import java.io.IOException

@SpringBootApplication
class ProciegosApplication
    fun main(args: Array<String>) {
        SpringApplication.run(ProciegosApplication::class.java,*args)

        try {
            val classloader: ClassLoader = ProciegosApplication::class.java.classLoader

            val file = File(classloader.getResource("servicekey.json")!!.file)
            val serviceAccount = FileInputStream(file.absolutePath)

            val options: FirebaseOptions = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("gs://prociegosysordos-a7cff.appspot.com")
                .build()

            if (FirebaseApp.getApps().isEmpty())
                FirebaseApp.initializeApp(options)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

