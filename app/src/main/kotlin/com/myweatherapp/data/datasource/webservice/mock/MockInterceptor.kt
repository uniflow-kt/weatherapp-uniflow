package com.myweatherapp.data.datasource.webservice.mock

import com.myweatherapp.common.file.JsonReader
import okhttp3.*

class MockInterceptor(private val reader: JsonReader) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().toString()
        val file = extractUrlToFile(url)
        val content = reader.getFileContent("$file.json")
        return Response.Builder()
                .code(200)
                .body(ResponseBody.create(MediaType.parse("application/json"), content))
                .request(chain.request())
                .message(content)
                .protocol(Protocol.HTTP_1_1)
                .build()
    }
}

fun extractUrlToFile(url: String): String {
    val names = url.split("/").toMutableList()
    names.remove("")
    names.remove("https:")
    names.remove("http:")
    val filtered = names.map {
        it.replace("?", "_").replace("=", "_")
    }
    return filtered.last().toLowerCase()
}
