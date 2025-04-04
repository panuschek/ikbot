package io.github.panuschek.ikbot.tbot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.InputChain
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.chain.BaseStatefulLink
import eu.vendeli.tgbot.types.chain.BreakCondition
import eu.vendeli.tgbot.types.component.ProcessedUpdate

@InputChain
object NewEventChain {
    object EventName : BaseStatefulLink() {
        override val breakCondition = BreakCondition { _, update, _ -> update.text.isBlank() }

        override suspend fun breakAction(user: User, update: ProcessedUpdate, bot: TelegramBot) {
            message { "Bitte gib einen Namen für die Vernstaltung ein." }
                .send(user, bot)
        }

        override suspend fun action(user: User, update: ProcessedUpdate, bot: TelegramBot): String {

        }
    }
}