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
import com.example.khazarmessenger.viemodel.LoginViewModel
import com.example.khazarmessenger.viemodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var textViewToRegister: TextView
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var singInBtn: Button
    private lateinit var viewModel : LoginViewModel
    private val repository = Repository()
    private val factory =  LoginViewModelFactory(repository)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        //initialize
        textViewToRegister = findViewById(R.id.login_tv)
        emailEt = findViewById(R.id.email_et)
        passwordEt = findViewById(R.id.password_et)
        singInBtn = findViewById(R.id.sing_in_btn)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)


        //check if user logged in before
        viewModel.checkUser()
        viewModel.isLoggedIn.observe(this, {isLogged->
            if(isLogged){
                val intentToMainActivity = Intent(this,MainActivity::class.java)
                startActivity(intentToMainActivity)
                finish()
            }
        })

        //click listeners
        textViewToRegister.setOnClickListener{
            val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
        }

        singInBtn.setOnClickListener {
            val email = emailEt.text
            val password = passwordEt.text

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Empty fields are not allowed here", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.loginUser(email.toString(), password.toString(),this)

                //control result
                viewModel.loginResult.observe(this, Observer { result->
                    if(result){
                        //login successfull
                        val intentToMainActivity = Intent(this,MainActivity::class.java)
                        startActivity(intentToMainActivity)
                        finish()
                    }else{
                        //login failed
                    }
                })
            }


        }
    }

}