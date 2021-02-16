package com.example.zervice

import java.io.BufferedReader
import java.io.InputStream
import java.io.PrintWriter
import java.net.Socket

data class Device (
    var name : String,
    var ipAddress: String
    //var stdIn : BufferedReader,
    //var stdOut: PrintWriter
)