package awesome

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/awesome/api/stuff/master/hello").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Requested key not found!", response.content)
            }
        }
    }
}
