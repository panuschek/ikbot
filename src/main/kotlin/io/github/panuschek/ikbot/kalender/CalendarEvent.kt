package io.github.panuschek.ikbot.kalender

import kotlinx.datetime.LocalDateTime

data class CalendarEvent(
    var calendarEventId: Long? = null,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val timeZone: String = "",
    val title: String = "",
    val subCalendars: List<String> = emptyList(),
    val description: String = "",
    val who: String = "",
    val where: String = "",
    val wholeDay: Boolean = false,
    val links: List<String> = emptyList()
)
