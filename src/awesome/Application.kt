package awesome

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.http.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val masterIp: String = System.getenv("MASTER_IP") ?: "0.0.0.0"

    val manager = RequestManager(masterIp)

    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }

    install(ContentNegotiation) {
    }

    routing {
        route("/awesome/api/stuff"){
            get("/{key}") {
                val value = manager.retrieveFromMaster(call.parameters.keyForStuff())
                call.respondText(value, contentType = ContentType.Text.Plain)
            }
            post("/{key}") {
                val value = call.receive<String>()
                manager.storeToMaster(call.parameters.keyForStuff(), value)
                call.respondText("Param Added", contentType = ContentType.Text.Plain)
            }
            //Routes used internally by all nodes to reach the master node
            route("/master"){
                get("/{key}") {
                    val value = manager.retrieve(call.parameters.keyForStuff())
                    call.respondText(value, contentType = ContentType.Text.Plain)
                }
                post("/{key}") {
                    val value = call.receive<String>()
                    manager.store(call.parameters.keyForStuff(), value)
                    call.respondText("Param Added", contentType = ContentType.Text.Plain)
                }
            }
        }

    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.localizedMessage)
        }
        exception<IllegalArgumentException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
        }
    }
}

fun Parameters.keyForStuff() = this["key"]?: throw IllegalArgumentException("'key' param required!")

