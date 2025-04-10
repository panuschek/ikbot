package io.github.panuschek.ikbot.tbot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.sendMessage
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.component.CallbackQueryUpdate
import eu.vendeli.tgbot.utils.common.setChain
import io.github.panuschek.ikbot.kalender.CalendarClient
import org.springframework.stereotype.Component

@Component
class BotController(
    private val calendarClient: CalendarClient
){
    init {
        NewEventChain.calendarClient = calendarClient
    }

    @CommandHandler(["/start"])
    suspend fun start(user: User, bot: TelegramBot) {
        sendMessage { "Willkommen" }
            .inlineKeyboardMarkup {
                "Neue Veranstaltung" callback "newEvent"
            }
            .send(user, bot)
    }

    @CommandHandler.CallbackQuery(["newEvent"])
    suspend fun newEvent(
        upd: CallbackQueryUpdate,
        user: User,
        bot: TelegramBot
    ) {
        sendMessage { "Wie soll die Veranstaltung heißen?" }.send(user, bot)
        bot.inputListener.setChain(user = user, firstLink = NewEventChain.EventName)
    }
}