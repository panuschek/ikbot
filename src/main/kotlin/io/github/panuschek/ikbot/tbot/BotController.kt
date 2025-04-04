package io.github.panuschek.ikbot.tbot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.sendMessage
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.component.CallbackQueryUpdate
import org.springframework.stereotype.Component

@Component
class BotController{
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
        sendMessage { "Neue Veranstaltung" }.send(user, bot)
    }
}