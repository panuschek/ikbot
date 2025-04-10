package io.github.panuschek.ikbot.tbot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.InputChain
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.api.message.sendMessage
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.chain.BaseStatefulLink
import eu.vendeli.tgbot.types.chain.BreakCondition
import eu.vendeli.tgbot.types.chain.ChainLink
import eu.vendeli.tgbot.types.component.ProcessedUpdate
import eu.vendeli.tgbot.utils.common.getInstance
import io.github.panuschek.ikbot.kalender.CalendarClient
import org.springframework.stereotype.Component

@Component
@InputChain
object NewEventChain {
    lateinit var calendarClient: CalendarClient

    private val dateRegex = Regex("\\d{2}.\\d{2}.\\d{4}")
    private val timeRegex = Regex("\\d{2}:\\d{2}")

    @Component
    object EventName : BaseStatefulLink() {
        override val breakCondition = BreakCondition { _, update, _ -> update.text.isBlank() }
        override suspend fun breakAction(user: User, update: ProcessedUpdate, bot: TelegramBot) {
            message {
                "Bitte gib einen Namen für die Veranstaltung ein."
            }.send(user, bot)
        }

        override suspend fun action(user: User, update: ProcessedUpdate, bot: TelegramBot): String {
            message {
                "Wo findet die Veranstaltung statt?"
            }.send(user, bot)

            return update.text
        }
    }

    @Component
    object EventLocation : BaseStatefulLink() {
        override val breakCondition = BreakCondition { _, update, _ -> update.text.isBlank() }
        override suspend fun breakAction(user: User, update: ProcessedUpdate, bot: TelegramBot) {
            message {
                "Bitte gib einen Ort für die Veranstaltung ein."
            }.send(user, bot)
        }

        override suspend fun action(user: User, update: ProcessedUpdate, bot: TelegramBot): String {
            message {
                "An welchem Datum findet die Veranstaltung statt (z.B. 01.01.2025)?"
            }.send(user, bot)

            return update.text
        }
    }

    @Component
    object EventDate : BaseStatefulLink() {
        override val breakCondition = BreakCondition { _, update, _ -> !update.text.matches(dateRegex) }

        override suspend fun breakAction(user: User, update: ProcessedUpdate, bot: TelegramBot) {
            message {
                "Bitte antworte mit einem Datum im Format 01.01.2025"
            }.send(user, bot)
        }

        override suspend fun action(user: User, update: ProcessedUpdate, bot: TelegramBot): String {
            message { "Um wie viel Uhr beginnt die Veranstaltung (z.B. 21:00)?" }.send(user, bot)

            return update.text
        }
    }

    @Component
    object EventStartTime : BaseStatefulLink() {
        override val breakCondition = BreakCondition { _, update, _ -> !update.text.matches(timeRegex) }

        override suspend fun breakAction(user: User, update: ProcessedUpdate, bot: TelegramBot) {
            message {
                "Bitte antworte mit einer Uhrzeit im Format 19:00"
            }.send(user, bot)
        }

        override suspend fun action(user: User, update: ProcessedUpdate, bot: TelegramBot): String {
            message { "Um wie viel Uhr endet die Veranstaltung (z.B. 06:00)?" }.send(user, bot)

            return update.text
        }
    }

    @Component
    object EventEndTime : BaseStatefulLink() {
        override val breakCondition = BreakCondition { _, update, _ -> !update.text.matches(timeRegex) }

        override suspend fun breakAction(user: User, update: ProcessedUpdate, bot: TelegramBot) {
            message {
                "Bitte antworte mit einer Uhrzeit im Format 06:00"
            }.send(user, bot)
        }

        override suspend fun action(user: User, update: ProcessedUpdate, bot: TelegramBot): String {
            message { "Wenn du möchtest, kannst du mir jetzt noch ein Photo von einem Flyer schicken." }.send(user, bot)
            return update.text
        }
    }

    @Component
    object Final : ChainLink() {
        override suspend fun action(user: User, update: ProcessedUpdate, bot: TelegramBot) {
            val eventName = bot.getInstance(EventName::class).state.get(user)
            val eventLocation = bot.getInstance(EventLocation::class).state.get(user)
            val eventDate = bot.getInstance(EventDate::class).state.get(user)
            val eventStartTime = bot.getInstance(EventStartTime::class).state.get(user)
            val eventEndTime = bot.getInstance(EventEndTime::class).state.get(user)

            message {
                """
                    Ich habe folgende Veranstaltung in den Kalender eingetragen:
                    Name: $eventName
                    Ort: $eventLocation
                    Datum: $eventDate $eventStartTime - $eventEndTime
                """.trimIndent()
            }.send(user, bot)
        }
    }
}