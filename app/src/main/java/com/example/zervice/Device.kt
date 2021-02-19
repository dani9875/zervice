package com.example.zervice

import java.io.BufferedReader
import java.io.InputStream
import java.io.PrintWriter
import java.net.Socket

data class Device (
    var ipAddress: String,
    var name : String,
    var connection: Socket
)