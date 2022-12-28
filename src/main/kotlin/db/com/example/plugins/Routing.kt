package db.com.example.plugins


import db.com.example.data.dao
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.util.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("articles") {

            get {
                call.respond( dao.allArticles())
                   // FreeMarkerContent("index.ftl", mapOf("articles" to dao.allArticles())))
            }

            post {
                val formParameters = call.receiveParameters()
                val title = formParameters.getOrFail("title")
                val body = formParameters.getOrFail("body")
                val article = dao.addNewArticle(title, body)
                call.respondRedirect("/articles/${article?.id}")
            }

            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
               // call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.article(id))))
               // call.respond(mapOf( dao.article(id)))
            }
            get("{id}/edit") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                //call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.article(id))))
                //call.respond(dao.article(id))
            }

            post("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val formParameters = call.receiveParameters()
                when (formParameters.getOrFail("_action")) {
                    "update" -> {
                        val title = formParameters.getOrFail("title")
                        val body = formParameters.getOrFail("body")
                        dao.editArticle(id, title, body)
                        call.respondRedirect("/articles/$id")
                    }

                    "delete" -> {
                        dao.deleteArticle(id)
                        call.respondRedirect("/articles")
                    }
                }

            }
        }
    }
}
