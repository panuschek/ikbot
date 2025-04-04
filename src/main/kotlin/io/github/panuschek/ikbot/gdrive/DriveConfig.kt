package io.github.panuschek.ikbot.gdrive

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.ServiceAccountCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class DriveConfig {
    @Bean
    fun drive(): Drive {
        val jsonFactory = GsonFactory.getDefaultInstance()
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()

        val credentials = ServiceAccountCredentials
            .fromStream(ClassPathResource("credentials.json").inputStream)
            .createScoped(listOf(DriveScopes.DRIVE))

        return Drive.Builder(httpTransport, jsonFactory, HttpCredentialsAdapter(credentials))
            .setApplicationName("ikbot")
            .build()
    }
}