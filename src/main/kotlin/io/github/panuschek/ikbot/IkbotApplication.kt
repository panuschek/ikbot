package io.github.panuschek.ikbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IkbotApplication

fun main(args: Array<String>) {
	runApplication<IkbotApplication>(*args)
}
