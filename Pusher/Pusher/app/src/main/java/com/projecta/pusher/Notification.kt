package com.projecta.pusher

import android.os.StrictMode
import android.provider.MediaStore
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response;
import org.json.JSONObject
//val key: String
class Notification() {
    val apiUrl: String = "https://fcm.googleapis.com/fcm/send"
    var apiKey = ""
    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    var client: OkHttpClient = OkHttpClient()

    init {
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        StrictMode.setThreadPolicy(policy)
//        apiKey = key;
    }


    fun sendNotification(token: String, title: String, body: String) {
        print(token)
        val payload =
            mapOf("to" to token, "notification" to mapOf("title" to title, "body" to body))
        print(post(JSONObject(payload).toString()))
    }

    fun post(json: String)  {
        val body: RequestBody = json.toRequestBody(JSON)
        val request: Request =
            Request.Builder().url(apiUrl).addHeader("Authorization", "key=" + apiKey).post(body)
                .build()

       val response: Response = client.newCall(request).execute()

        println(response.body!!.string())
    }
}