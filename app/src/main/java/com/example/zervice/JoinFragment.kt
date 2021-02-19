package com.example.zervice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.join_fragment.*
import java.io.PrintWriter


class JoinFragment(private var device: Device) : DialogFragment(), Runnable {
    private var running = true;
    lateinit var user: String;
    lateinit var password: String;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.join_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            Toast.makeText(requireContext(), "Wrote credentials to outputstream", Toast.LENGTH_LONG).show()
            val t = Thread(this)
            t.start()
        }
    }

    override fun run() {
        var out = PrintWriter(device.connection.getOutputStream(), true)
        out.println("Username: " + etUserName.text + " Password: " + etPassword.text)
        //Thread.currentThread().interrupt();
    }

}