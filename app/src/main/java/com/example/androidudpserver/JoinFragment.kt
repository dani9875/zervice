package com.example.androidudpserver

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class JoinFragment(private var device : Device) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.join_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       requireActivity().findViewById<Button>(R.id.buttonOk)?.setOnClickListener {
            val toast = Toast.makeText(requireContext(),"Do whatever you want to do with "+ device.name, Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.BOTTOM,0,0)
                    toast.show()
        }
    }
}