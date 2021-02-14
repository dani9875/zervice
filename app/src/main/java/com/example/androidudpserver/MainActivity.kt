package com.example.androidudpserver

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity(),DevicesAdapter.DeviceItemClickListener {

    var tcpServer: TCPServerThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TAG", "starting act")
        val devicesAdapter = DevicesAdapter(this)
        val recView = findViewById<RecyclerView>(R.id.rvDevices)
        recView.layoutManager = LinearLayoutManager(this)
        recView.adapter = devicesAdapter

    }

    override fun onStart() {
        super.onStart()

        val thread = Thread(Runnable {
            try {
                tcpServer = TCPServerThread()
                tcpServer!!.start()
                Log.d("TAG", "starting thread")
                Toast.makeText(applicationContext," Starting Server from Main", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()


    }

    override fun onStop() {
        super.onStop()
        if (tcpServer != null) {
            tcpServer = null
        }
    }

    inner class TCPServerThread : Thread() {
        private var server: ServerSocket? = null
        private var w: ClientWorker? = null

        init{
            listenSocket()
        }

        private fun listenSocket() {
            try {
                server = ServerSocket(tcpPORT)
                server!!.reuseAddress = true
            } catch (e: IOException) {
                println("Could not listen on port 4444")
                System.exit(-1)
            }
            while (true) {
                try {
                    w = ClientWorker(server!!.accept())
                    Log.d("TAG", "Client Accept done")
                    val t = Thread(w)
                    t.start()
                } catch (e: IOException) {
                    println("Accept failed: 52222")
                    System.exit(-1)
                }
            }
        }
    }
        inner class ClientWorker /*Constructor*/(private val client: Socket) : Runnable {
            override fun run() {
                var line: String
                var `in`: BufferedReader? = null
                var out: PrintWriter? = null
                try {
                    `in` = BufferedReader(InputStreamReader(client.getInputStream()))
                    out = PrintWriter(client.getOutputStream(), true)
                } catch (e: IOException) {
                    println("in or out failed")
                    System.exit(-1)
                }

                while (true) {
                    try {
                        line = `in`!!.readLine()
                        out!!.println(line)
                    } catch (e: IOException) {
                        println("Read failed")
                        System.exit(-1)
                    }
                }
            }
        }

    companion object {
        private const val TAG = "asd" //MainActivity.class.getSimpleName();
        const val tcpPORT = 4446
    }

    override fun onItemClick(clickedStore: Device) {
        TODO("Not yet implemented")

    }
}