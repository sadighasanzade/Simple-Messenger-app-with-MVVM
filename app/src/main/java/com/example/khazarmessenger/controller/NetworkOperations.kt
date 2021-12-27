package com.example.khazarmessenger.controller

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import com.example.khazarmessenger.model.Message
import com.example.khazarmessenger.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CountDownLatch

class NetworkOperations {
    private lateinit var auth: FirebaseAuth;
    private lateinit var databaseRef: DatabaseReference

    suspend fun registerUser(email: String, password: String,fullName: String, activity: Activity): Boolean{
        var result = false
        databaseRef = Firebase.database.reference
        val dialog: ProgressDialog = ProgressDialog(activity)
        dialog.setTitle("Register")
        dialog.setMessage("Wait...")
        dialog.show()

        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setTitle("Register")
        alertDialog.setMessage("Check your connection or try another e-mail")
        alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener{
                d, which ->
            dialog.dismiss()

        })
        alertDialog.create()
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {task->
                if(task.isSuccessful){
                    val userId = auth.currentUser!!.uid
                    //add user to db
                    val user = User(userId,fullName,email)
                    databaseRef.child("Users").child(userId).setValue(user)
                    result = true

                }else{
                    result = false
                    alertDialog.show()
                }
            }
            .await()
        Log.d("sado", "registerUser: "+result)
        dialog.dismiss()
        return result
    }

    suspend fun loginUser(email:String, password: String,context: Context):Boolean{
        var result =    false
        val dialog: ProgressDialog = ProgressDialog(context)
        dialog.setTitle("Login")
        dialog.setMessage("wait...")
        dialog.show()
        Firebase.auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context,"Logged in.",Toast.LENGTH_SHORT).show()
                    result = true
                }else{
                    dialog.dismiss()
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
            .await()


        dialog.dismiss()

        return result;
    }

    suspend fun isLoggedIn():Boolean{
        auth = Firebase.auth
        val user = auth.currentUser
        if(user!=null){
            return true
        }
        return false
    }
    suspend fun getCurrentUserId(): String{
        val user = Firebase.auth.currentUser
        return user!!.uid
    }

    suspend fun logOutUser() {
        Firebase.auth.signOut()
    }

    suspend fun getAllUsers(listener: onDataListener){
        var list = ArrayList<User?>()
        databaseRef = Firebase.database.reference
        databaseRef.child("Users").addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(postSnapShot in snapshot.children){
                    val user = postSnapShot.getValue(User::class.java)
                    if(Firebase.auth.currentUser!!.uid != user?.userId) {
                        list.add(user)
                    }
                    listener.onCompleted(list)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                listener.onFailed()
            }
        })

    }

    suspend fun sendMessage(message: Message, listener: onMessageUpdate) {
        databaseRef = Firebase.database.reference
        val senderRoom = message.sendId + message.recieverId
        val receiverRoom = message.recieverId + message.sendId

        databaseRef.child("chats").child(senderRoom).child("messages").push()
            .setValue(message).addOnSuccessListener {
                databaseRef.child("chats").child(receiverRoom).child("messages").push()
                    .setValue(message)
            }

        updateData(listener, senderRoom)
    }

     fun updateData(listener: onMessageUpdate, senderRoom: String) {
        val messages: ArrayList<Message?> = ArrayList()
        databaseRef = Firebase.database.reference
        databaseRef.child("chats").child(senderRoom).child("messages").addValueEventListener(
            object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messages.clear()
                    for(post in snapshot.children){
                        val message = post.getValue(Message::class.java)
                        messages.add(message)
                    }
                    listener.onCompleted(messages)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
        )

    }

}
interface onDataListener{
    fun onCompleted(list:ArrayList<User?>)
    fun onFailed()
}

interface onMessageUpdate{
    fun onCompleted(list: ArrayList<Message?>)
}