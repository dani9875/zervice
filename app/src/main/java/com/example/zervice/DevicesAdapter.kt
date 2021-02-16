package com.example.zervice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zervice.R

class DevicesAdapter(private var itemClickListener: DeviceItemClickListener) : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {

    private val deviceList = mutableListOf<Device>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.device_rowitem, parent, false)
        return DeviceViewHolder(view)
    }

    override fun getItemCount() = deviceList.size

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = deviceList[position]
        holder.device = device
        holder.tvIPAddress.text = device.ipAddress
        holder.tvName.text = device.name

        holder.buttonJoin.setOnClickListener {
                device?.let { device -> itemClickListener?.onItemClick(device) }
            }
        }


    inner class DeviceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvIPAddress: TextView = itemView.findViewById<TextView>(R.id.tvIPAddress)
        val tvName: TextView = itemView.findViewById<TextView>(R.id.tvName)
        val buttonJoin: Button = itemView.findViewById<Button>(R.id.btJoin)
        var device :Device? = null

        init {
            itemView.setOnClickListener {
                device?.let { device -> itemClickListener?.onItemClick(device) }
            }
        }
    }

    fun addItem(device: Device) {
        val size = deviceList.size
        deviceList.add(device)
        notifyItemInserted(size)
    }

    interface DeviceItemClickListener{
        fun onItemClick(clickedStore: Device)
    }

}