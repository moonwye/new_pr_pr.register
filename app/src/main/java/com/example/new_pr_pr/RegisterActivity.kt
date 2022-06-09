package com.example.new_pr_pr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rey.material.widget.CheckBox

class RegisterActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers:DatabaseReference
    private var firebaseUserID : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        val spinner: Spinner = findViewById(R.id.planets_spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val Reg_btn = findViewById<Button>(R.id.reg_btn)

        mAuth = FirebaseAuth.getInstance()
        Reg_btn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {

        val FullName_users = findViewById<EditText>(R.id.fullName_user)
        val Email_users = findViewById<EditText>(R.id.email_user)
        val Password_users = findViewById<EditText>(R.id.password_user)
        val Login_users = findViewById<EditText>(R.id.login_user)
        val Phone_users = findViewById<EditText>(R.id.phone_user)
        val Password_repeat_users = findViewById<EditText>(R.id.password_user_repeat)
        val Ras_users = findViewById<EditText>(R.id.ras_user)
        val Spinner_users = findViewById<Spinner>(R.id.planets_spinner)
        val check_reg = findViewById<CheckBox>(R.id.Big_form_checkbox)

        val fullName_edit : String = FullName_users.text.toString()
        val ras_edit : String = Ras_users.text.toString()
        val email_edit : String = Email_users.text.toString()
        val password_edit : String = Password_users.text.toString()
        val login_edit : String = Login_users.text.toString()
        val phone_edit : String = Phone_users.text.toString()
        val password_repeat_edit : String = Password_repeat_users.text.toString()
        val spinner_edit : String = Spinner_users.toString()

        if(fullName_edit=="")
        {
            Toast.makeText(this,"Введите ФИО", Toast.LENGTH_LONG).show()
        }
        else if(login_edit=="")
        {
            Toast.makeText(this,"Введите логин", Toast.LENGTH_LONG).show()
        }
        else if(phone_edit=="")
        {
            Toast.makeText(this,"Введите номер телефона", Toast.LENGTH_LONG).show()
        }
        else if(email_edit=="")
        {
            Toast.makeText(this,"Введите адрес почты", Toast.LENGTH_LONG).show()
        }
        else if(password_edit=="")
        {
            Toast.makeText(this,"Введите пароль", Toast.LENGTH_LONG).show()
        }
        else if(password_repeat_edit=="")
        {
            Toast.makeText(this,"Введите пароль подтвеждения", Toast.LENGTH_LONG).show()
        }
        else if(password_edit != password_repeat_edit)
        {
            Toast.makeText(this,"Пароли не совпадают",Toast.LENGTH_LONG).show()
        }
        else if(ras_edit=="")
        {
            Toast.makeText(this, "Введите информацию о себе", Toast.LENGTH_LONG).show()
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
                        userHashMap["password"] = password_edit//.hashCode() Доделать хэширование пароля в базу данных
                        userHashMap["login"] = login_edit
                        userHashMap["phone"] = phone_edit
                        userHashMap["class_user"] = spinner_edit
                        userHashMap["info_user"] = ras_edit

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val regIntent = Intent(this, HomeActivity::class.java)
                                    startActivity(regIntent)
                                    Toast.makeText(this,"Приятного использования <3",Toast.LENGTH_LONG).show()
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