package com.example.khazarmessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khazarmessenger.adapter.UserAdapter
import com.example.khazarmessenger.controller.onDataListener
import com.example.khazarmessenger.model.User
import com.example.khazarmessenger.repository.Repository
import com.example.khazarmessenger.viemodel.MainViewModel
import com.example.khazarmessenger.viemodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val repository = Repository()
    private val factory : MainViewModelFactory = MainViewModelFactory(repository)
    private lateinit var viewModel: MainViewModel
    private lateinit var recylerView: RecyclerView
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //INIT
        val emptyList = ArrayList<User?>()
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)
        adapter = UserAdapter(emptyList,this)
        recylerView = findViewById(R.id.user_list)
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.adapter = adapter

        refreshUi()



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.log_out) {
            viewModel.logOut()
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
            return true
        }
        else if(item.itemId == R.id.update){
            refreshUi()
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    fun refreshUi(){
        //update users
        viewModel.getAllUsers(object : onDataListener{
            override fun onCompleted(list: ArrayList<User?>) {
                adapter.setUsers(list)
                adapter.notifyDataSetChanged()
            }

            override fun onFailed() {
                adapter.setUsers(ArrayList<User?>())
                adapter.notifyDataSetChanged()
            }
        })



    }
}