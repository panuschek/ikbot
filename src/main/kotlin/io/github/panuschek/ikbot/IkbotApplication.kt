package io.github.panuschek.ikbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class IkbotApplication

fun main(args: Array<String>) {
	runApplication<IkbotApplication>(*args)
}
