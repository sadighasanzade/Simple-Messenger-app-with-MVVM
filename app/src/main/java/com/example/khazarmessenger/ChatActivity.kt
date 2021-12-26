package com.example.khazarmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khazarmessenger.adapter.MessageAdapter
import com.example.khazarmessenger.controller.onMessageUpdate
import com.example.khazarmessenger.repository.Repository
import com.example.khazarmessenger.viemodel.ChatViewModel
import com.example.khazarmessenger.viemodel.ChatViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.khazarmessenger.model.Message

class ChatActivity : AppCompatActivity() {
    private lateinit var messageLayout : TextInputLayout
    private lateinit var messageEt : TextInputEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessageAdapter
    private lateinit var viewModel : ChatViewModel
    private lateinit var userId: String
    private lateinit var receiverId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageLayout = findViewById(R.id.write_message)
        messageEt = findViewById(R.id.message_et)
        recyclerView = findViewById(R.id.messages)
        val repository = Repository()
        val factory = ChatViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(ChatViewModel::class.java)
        viewModel.getUid()
        viewModel.uid.observe(this, Observer {
            //settin ui
            userId = it
            adapter = MessageAdapter(ArrayList<Message?>(),userId)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            setMessages(userId+receiverId)
        })

        receiverId = intent.getStringExtra("uid").toString()
        supportActionBar?.title = intent.getStringExtra("name").toString()

        //sending messages
        messageLayout.setEndIconOnClickListener {
            val msg = messageEt.text.toString()
            val senderUid = userId
            val receiverUid = receiverId
            val message = Message(senderUid,receiverUid,msg)
            viewModel.sendMessage(message, object :onMessageUpdate{
                override fun onCompleted(list: ArrayList<Message?>) {
                    adapter.setMessages(list)
                    adapter.notifyDataSetChanged()
                    messageEt.setText("")
                }

            })
        }
    }

    fun setMessages(room:String){
        viewModel.getAllMessages(room,object:onMessageUpdate{
            override fun onCompleted(list: ArrayList<Message?>) {
                adapter.setMessages(list)
                adapter.notifyDataSetChanged()
            }
        })
    }
}