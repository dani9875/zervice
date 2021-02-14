package com.example.androidudpserver;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "asd"; //MainActivity.class.getSimpleName();

    TextView infoIp, infoPort;
    TextView textViewState, textViewPrompt;

    static final int tcpPORT = 52222;
    TCPServerThread tcpServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        tcpServer = new TCPServerThread();
        tcpServer.start();
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        if(tcpServer != null)
        {
            tcpServer = null;
        }

        super.onStop();
    }

    private class TCPServerThread extends Thread
    {
        ServerSocket server;
        ClientWorker w;

        public TCPServerThread() {
            super();
            listenSocket();
        }

        public void listenSocket() {
            try {
                server = new ServerSocket(tcpPORT);
                server.setReuseAddress(true);
            } catch (IOException e) {
                System.out.println("Could not listen on port 4444");
                System.exit(-1);
            }

            while (true) {
                try {
                    w = new ClientWorker(server.accept());
                    Thread t = new Thread(w);
                    t.start();
                } catch (IOException e) {
                    System.out.println("Accept failed: 52222");

                    System.exit(-1);
                }
            }
        }


        class ClientWorker implements Runnable {
            private Socket client;

            /*Constructor*/
            ClientWorker(Socket client) {
                this.client = client;
            }

            @Override
            public void run() {
                String line;
                BufferedReader in = null;
                PrintWriter out = null;

                try {
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out = new PrintWriter(client.getOutputStream(), true);
                } catch (IOException e) {
                    System.out.println("in or out failed");
                    System.exit(-1);
                }

                while (true) {
                    try {
                        line = in.readLine();

                        out.println(line);
                    } catch (IOException e) {
                        System.out.println("Read failed");
                        System.exit(-1);
                    }
                }
            }
        }

    }
}