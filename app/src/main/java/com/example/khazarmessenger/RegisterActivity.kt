package com.example.khazarmessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.khazarmessenger.repository.Repository
import com.example.khazarmessenger.viemodel.RegisterViewModel
import com.example.khazarmessenger.viemodel.RegisterViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var textViewToLogIn: TextView
    private lateinit var emailEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpBtn: Button
    private lateinit var viewModel : RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        //init
        textViewToLogIn = findViewById(R.id.register_tv)
        emailEditText = findViewById(R.id.email_et)
        nameEditText = findViewById(R.id.name_et)
        passwordEditText = findViewById(R.id.password_et)
        signUpBtn = findViewById(R.id.sign_up_btn)
        val repository = Repository()
        val viewModelFactory = RegisterViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RegisterViewModel::class.java)

        //click listeners

        textViewToLogIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {
            val email = emailEditText.text
            val password = passwordEditText.text
            val name = nameEditText.text

            if(email.isEmpty() || password.isEmpty() || name.isEmpty()){
                Toast.makeText(this, "empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }else{
                //sign up user
                viewModel.registerUser(email = email.toString(), pass = password.toString(), fullName = name.toString(), this@RegisterActivity)

                //check result
                viewModel.registerResult.observe(this, Observer { result->
                    if(result){
                        //register is successfull
                        Toast.makeText(
                            this,
                            "Register finished successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        //register is unsuccessfull
                    }
                })
            }
        }
    }
}