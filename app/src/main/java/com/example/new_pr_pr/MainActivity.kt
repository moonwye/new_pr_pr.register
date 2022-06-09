package com.example.new_pr_pr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val log_btn = findViewById<Button>(R.id.login_btn)
        log_btn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val Email_users = findViewById<EditText>(R.id.login_email_input)
        val Password_users = findViewById<EditText>(R.id.login_password_input)

        val email_edit : String = Email_users.text.toString()
        val password_edit : String = Password_users.text.toString()

        if(email_edit=="")
        {
            Toast.makeText(this,"Введите адрес почты", Toast.LENGTH_LONG).show()
        }
        else if(password_edit=="")
        {
            Toast.makeText(this,"Введите пароль", Toast.LENGTH_LONG).show()
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email_edit,password_edit)
                .addOnCompleteListener { task->
                    if(task.isSuccessful)
                    {
                        val regIntent = Intent(this, HomeActivity::class.java)
                        startActivity(regIntent)
                        Toast.makeText(this,"Приятного использования <3, не забудьте при следующем входе в аккаунт нажать кнопку запомнить меня)",Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this,"Ошибка: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    fun reg(view: View) {
        val regIntent = Intent(this, RegisterActivity::class.java)
        startActivity(regIntent)
    }
}