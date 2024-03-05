package com.example.cricdekho.data.remote

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketManager {
    private const val SOCKET_URL = "http://192.46.214.33:3005"
    private var socket: Socket? = null

    fun connect() {
        try {
            val options = IO.Options()
            socket = IO.socket(SOCKET_URL, options)
            socket?.connect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun emitEvent(eventName: String, data: Any?) {
        socket?.emit(eventName, data)
    }

    fun setEventListener(eventName: String, listener: (Any?) -> Unit) {
        socket?.on(eventName, listener)
    }

    fun removeEventListener(eventName: String) {
        socket?.off(eventName)
    }
}