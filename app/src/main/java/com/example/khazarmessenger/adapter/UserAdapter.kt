package com.example.khazarmessenger.adapter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.khazarmessenger.ChatActivity
import com.example.khazarmessenger.MainActivity
import com.example.khazarmessenger.R
import com.example.khazarmessenger.model.User

class UserAdapter(private var userList:ArrayList<User?>,private val context: Context)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name : TextView
        val profil : ImageView
        init {
            name = view.findViewById(R.id.username)
            profil = view.findViewById(R.id.profile_img)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_user, parent, false)

        return UserViewHolder(view)    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.name.text = userList[position]?.fullName

        holder.itemView.setOnClickListener{
            //TODO OPEN CHAT SCREEN
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name",userList[position]?.fullName)
            intent.putExtra("uid",userList[position]?.userId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUsers(list: ArrayList<User?>){
        userList = list
    }


}