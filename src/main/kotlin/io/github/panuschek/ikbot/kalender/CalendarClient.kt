package io.github.panuschek.ikbot.kalender

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(value = "calendar-client", url = "\${app.calendar-client.url}")
interface CalendarClient {
    @GetMapping("/event")
    suspend fun getEvents(): List<CalendarEvent>
}