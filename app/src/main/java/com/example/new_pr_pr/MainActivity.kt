package com.example.new_pr_pr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun reg(view: View) {
        val regIntent = Intent(this, RegisterActivity::class.java)
        startActivity(regIntent)
    }
}