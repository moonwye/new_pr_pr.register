package com.example.new_pr_pr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers:DatabaseReference
    private var firebaseUserID : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val Reg_btn = findViewById<Button>(R.id.reg_btn)

        mAuth = FirebaseAuth.getInstance()
        Reg_btn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val FullName_user = findViewById<EditText>(R.id.fullName_user)
        val Email_users = findViewById<EditText>(R.id.email_user)
        val Password_users = findViewById<EditText>(R.id.password_user)


        val fullName_edit : String = FullName_user.text.toString()
        val email_edit : String = Email_users.text.toString()
        val password_edit : String = Password_users.text.toString()

        if(fullName_edit=="")
        {
            Toast.makeText(this,"Введите ФИО", Toast.LENGTH_LONG).show()
        }
        else if(email_edit=="")
        {
            Toast.makeText(this,"Введите адрес почты", Toast.LENGTH_LONG).show()
        }
        else if(password_edit=="")
        {
            Toast.makeText(this,"Введите пароль", Toast.LENGTH_LONG).show()
        }

        else{
            mAuth.createUserWithEmailAndPassword(email_edit, password_edit)
                .addOnCompleteListener {
                        task ->
                    if(task.isSuccessful) {
                        firebaseUserID = mAuth.currentUser!!.uid
                        refUsers = FirebaseDatabase.getInstance().reference.child("Users")
                            .child(firebaseUserID)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["username"] = fullName_edit
                        userHashMap["email"] = email_edit

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val regIntent =
                                        Intent(this, HomeActivity::class.java)
                                    startActivity(regIntent)
                                    finish()

                                }
                            }
                    }
                    else
                    {
                        Toast.makeText(this,"Ошибка: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }

        }
    }
}