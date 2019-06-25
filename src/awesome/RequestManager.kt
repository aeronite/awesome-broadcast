package awesome

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url

/**
 * Internal logic for handling message requests.
 * Requests from external sources will be redirected to the master node.
 * The master node simply stores the values in a map
 */
class RequestManager(private val masterIp: String) {
    private val requests = mutableMapOf<String,String>()

    fun retrieve(key: String) = requests[key]?: "Requested key not found!"

    fun store(key: String, value: String) {
        requests[key] = value
    }

    suspend fun retrieveFromMaster(key: String) = HttpClient(CIO).use { client ->
        client.get<String>("http://$masterIp:8080/awesome/api/stuff/master/$key")
    }

    suspend fun storeToMaster(key: String, value: String)  = HttpClient(CIO).use { client ->
        client.post<Unit> {
            url("http://$masterIp:8080/awesome/api/stuff/master/$key")
            body = value
        }
    }
}