package db.com.example

import db.com.example.data.DatabaseFactory
import io.ktor.server.application.*
import db.com.example.plugins.*
import io.ktor.serialization.kotlinx.json.*

import io.ktor.server.application.hooks.CallFailed.install
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(ContentNegotiation){
        json()
    }
    //https://ktor.io/docs/freemarker.html#use_template
   /* install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE

    }*/
    DatabaseFactory.init()
    configureRouting()
}
