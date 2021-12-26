package com.example.khazarmessenger.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.khazarmessenger.R
import com.example.khazarmessenger.model.Message

class MessageAdapter(private var messages : ArrayList<Message?>, private val userId:String)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val SENT_MSG = 1
    val RECEIVED_MSG = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == SENT_MSG){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_sent_msg, parent, false)
            Log.d("Sado", "onCreateViewHolder: send ")
            return MessageAdapter.SenderViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_received_msg, parent, false)
            Log.d("Sado", "onCreateViewHolder: receive ")

            return MessageAdapter.ReceiverViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == SENT_MSG){
            val viewholder = holder as SenderViewHolder
            viewholder.message.text = messages[position]?.message
            Log.d("Sado", "onBindViewHolder: send")
        }else{
            val viewholder = holder as ReceiverViewHolder
            viewholder.message.text = messages[position]?.message
            Log.d("Sado", "onBindViewHolder: receive")

        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        if(messages[position]?.sendId == userId){
            return SENT_MSG
        }else{
            return RECEIVED_MSG
        }
    }

    class SenderViewHolder(view: View): RecyclerView.ViewHolder(view){
        val message = view.findViewById<TextView>(R.id.list_item_sent_msg)
    }
    class ReceiverViewHolder(view: View): RecyclerView.ViewHolder(view){
        val message = view.findViewById<TextView>(R.id.received_msg)
    }

    fun setMessages(msg : ArrayList<Message?>){
        messages = msg
    }
}